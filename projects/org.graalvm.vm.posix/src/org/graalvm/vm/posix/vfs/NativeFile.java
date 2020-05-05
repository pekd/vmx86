/*
 * Copyright (c) 2019, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * The Universal Permissive License (UPL), Version 1.0
 *
 * Subject to the condition set forth below, permission is hereby granted to any
 * person obtaining a copy of this software, associated documentation and/or
 * data (collectively the "Software"), free of charge and under any and all
 * copyright rights in the Software, and any and all patent rights owned or
 * freely licensable by each licensor hereunder covering either (i) the
 * unmodified Software as contributed to or provided by such licensor, or (ii)
 * the Larger Works (as defined below), to deal in both
 *
 * (a) the Software, and
 *
 * (b) any piece of software and/or hardware listed in the lrgrwrks.txt file if
 * one is included with the Software each a "Larger Work" to which the Software
 * is contributed by such licensors),
 *
 * without restriction, including without limitation the rights to copy, create
 * derivative works of, display, perform, and distribute the Software and make,
 * use, sell, offer for sale, import, export, have made, and have sold the
 * Software and the Larger Work(s), and to sublicense the foregoing rights on
 * either these or other terms.
 *
 * This license is subject to the following condition:
 *
 * The above copyright notice and either this complete permission notice or at a
 * minimum a reference to the UPL must be included in all copies or substantial
 * portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package org.graalvm.vm.posix.vfs;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.Map;

import org.graalvm.vm.posix.api.Errno;
import org.graalvm.vm.posix.api.PosixException;
import org.graalvm.vm.posix.api.Timespec;
import org.graalvm.vm.posix.api.io.Fcntl;
import org.graalvm.vm.posix.api.io.Stat;
import org.graalvm.vm.posix.api.io.Statx;
import org.graalvm.vm.posix.api.io.StatxTimestamp;
import org.graalvm.vm.posix.api.io.Stream;
import org.graalvm.vm.util.BitTest;

public class NativeFile extends VFSFile {
    private Path absolutePath;

    public NativeFile(VFSDirectory parent, Path absolutePath) {
        super(parent, absolutePath.getFileName().toString(), 0, 0, 0755);
        this.absolutePath = absolutePath;
    }

    @Override
    public void chown(long owner, long group) throws PosixException {
        throw new PosixException(Errno.EPERM);
    }

    @Override
    public void chmod(int mode) throws PosixException {
        throw new PosixException(Errno.EPERM);
    }

    @Override
    public long getUID() throws PosixException {
        try {
            return (int) Files.getAttribute(absolutePath, "unix:uid", NOFOLLOW_LINKS);
        } catch (IOException e) {
            throw new PosixException(Errno.EIO);
        } catch (UnsupportedOperationException e) {
            return 0;
        }
    }

    @Override
    public long getGID() throws PosixException {
        try {
            return (int) Files.getAttribute(absolutePath, "unix:gid", NOFOLLOW_LINKS);
        } catch (IOException e) {
            throw new PosixException(Errno.EIO);
        } catch (UnsupportedOperationException e) {
            return 0;
        }
    }

    @Override
    public Stream open(boolean read, boolean write) throws PosixException {
        int flags = 0;
        if (read && !write) {
            flags |= Fcntl.O_RDONLY;
        } else if (!read && write) {
            flags |= Fcntl.O_WRONLY;
        } else if (read && write) {
            flags |= Fcntl.O_RDWR;
        } else {
            throw new PosixException(Errno.EINVAL);
        }
        return open(flags, 0);
    }

    @Override
    public Stream open(int flags, int mode) throws PosixException {
        return new NativeFileStream(absolutePath, flags);
    }

    @Override
    public long size() throws PosixException {
        try {
            return Files.getFileAttributeView(absolutePath, BasicFileAttributeView.class, NOFOLLOW_LINKS).readAttributes().size();
        } catch (IOException e) {
            throw new PosixException(Errno.EIO);
        }
    }

    @Override
    public void atime(Date time) throws PosixException {
        throw new PosixException(Errno.EPERM);
    }

    @Override
    public Date atime() throws PosixException {
        try {
            FileTime atime = Files.getFileAttributeView(absolutePath, BasicFileAttributeView.class, NOFOLLOW_LINKS).readAttributes().lastAccessTime();
            return new Date(atime.toMillis());
        } catch (IOException e) {
            throw new PosixException(Errno.EIO);
        }
    }

    @Override
    public void mtime(Date time) throws PosixException {
        throw new PosixException(Errno.EPERM);
    }

    @Override
    public Date mtime() throws PosixException {
        try {
            FileTime mtime = Files.getFileAttributeView(absolutePath, BasicFileAttributeView.class, NOFOLLOW_LINKS).readAttributes().lastModifiedTime();
            return new Date(mtime.toMillis());
        } catch (IOException e) {
            throw new PosixException(Errno.EIO);
        }
    }

    @Override
    public void ctime(Date time) throws PosixException {
        throw new PosixException(Errno.EPERM);
    }

    @Override
    public Date ctime() throws PosixException {
        try {
            FileTime ctime = (FileTime) Files.getAttribute(absolutePath, "unix:ctime", NOFOLLOW_LINKS);
            return new Date(ctime.toMillis());
        } catch (IOException e) {
            throw new PosixException(Errno.EIO);
        } catch (UnsupportedOperationException e) {
            return mtime();
        }
    }

    static void stat(Path path, Stat buf) throws PosixException {
        try {
            Map<String, Object> attrs = Files.readAttributes(path, "unix:*", NOFOLLOW_LINKS);
            buf.st_dev = (long) attrs.get("dev");
            buf.st_ino = (long) attrs.get("ino");
            buf.st_mode = (int) attrs.get("mode");
            buf.st_nlink = (int) attrs.get("nlink");
            buf.st_uid = (int) attrs.get("uid");
            buf.st_gid = (int) attrs.get("gid");
            buf.st_rdev = (long) attrs.get("rdev");
            buf.st_size = (long) attrs.get("size");
            buf.st_blksize = 4096;
            buf.st_blocks = (long) Math.ceil(buf.st_size / 512.0);
            buf.st_atim = new Timespec(new Date(((FileTime) attrs.get("lastAccessTime")).toMillis()));
            buf.st_mtim = new Timespec(new Date(((FileTime) attrs.get("lastModifiedTime")).toMillis()));
            buf.st_ctim = new Timespec(new Date(((FileTime) attrs.get("ctime")).toMillis()));
        } catch (IOException e) {
            throw new PosixException(Errno.EIO);
        } catch (UnsupportedOperationException e) {
            try {
                BasicFileAttributes info = Files.getFileAttributeView(path,
                                BasicFileAttributeView.class, NOFOLLOW_LINKS).readAttributes();
                File f = path.toFile();
                buf.st_dev = 0;
                buf.st_ino = 1;
                if (info.isRegularFile()) {
                    buf.st_mode = Stat.S_IFREG;
                } else if (info.isSymbolicLink()) {
                    buf.st_mode = Stat.S_IFLNK;
                } else if (info.isDirectory()) {
                    buf.st_mode = Stat.S_IFDIR;
                } else {
                    buf.st_mode = Stat.S_IFIFO;
                }
                if (f.canRead()) {
                    buf.st_mode |= Stat.S_IRUSR | Stat.S_IRGRP | Stat.S_IROTH;
                }
                if (f.canWrite()) {
                    buf.st_mode |= Stat.S_IWUSR | Stat.S_IWGRP | Stat.S_IWOTH;
                }
                if (f.canExecute()) {
                    buf.st_mode |= Stat.S_IXUSR | Stat.S_IXGRP | Stat.S_IXOTH;
                }
                buf.st_nlink = 1;
                buf.st_uid = 0;
                buf.st_gid = 0;
                buf.st_rdev = 0;
                buf.st_size = info.size();
                buf.st_blksize = 4096;
                buf.st_blocks = (long) Math.ceil(buf.st_size / 512.0);
                buf.st_atim = new Timespec(new Date(info.lastAccessTime().toMillis()));
                buf.st_mtim = new Timespec(new Date(info.lastModifiedTime().toMillis()));
                buf.st_ctim = new Timespec(new Date(info.lastModifiedTime().toMillis()));
            } catch (IOException ex) {
                throw new PosixException(Errno.EIO);
            }
        }
    }

    static void statx(Path path, int mask, Statx buf) throws PosixException {
        buf.stx_mask = 0;
        buf.stx_attributes = 0;
        buf.stx_attributes_mask = 0;

        try {
            Map<String, Object> attrs = Files.readAttributes(path, "unix:*", NOFOLLOW_LINKS);
            if (BitTest.test(mask, Stat.STATX_INO)) {
                buf.stx_ino = (long) attrs.get("ino");
                buf.stx_mask |= Stat.STATX_INO;
            }
            if (BitTest.test(mask, Stat.STATX_MODE) | BitTest.test(mask, Stat.STATX_TYPE)) {
                buf.stx_mode = (short) (int) attrs.get("mode");
                buf.stx_mask |= Stat.STATX_MODE | Stat.STATX_TYPE;
            }
            if (BitTest.test(mask, Stat.STATX_NLINK)) {
                buf.stx_nlink = (int) attrs.get("nlink");
                buf.stx_mask |= Stat.STATX_NLINK;
            }
            if (BitTest.test(mask, Stat.STATX_UID)) {
                buf.stx_uid = (int) attrs.get("uid");
                buf.stx_mask |= Stat.STATX_UID;
            }
            if (BitTest.test(mask, Stat.STATX_GID)) {
                buf.stx_gid = (int) attrs.get("gid");
                buf.stx_mask |= Stat.STATX_GID;
            }
            if (BitTest.test(mask, Stat.STATX_SIZE)) {
                buf.stx_size = (long) attrs.get("size");
                buf.stx_mask |= Stat.STATX_SIZE;
            }
            if (BitTest.test(mask, Stat.STATX_BLOCKS)) {
                buf.stx_blksize = 4096;
                buf.stx_blocks = (long) Math.ceil(buf.stx_size / 512.0);
                buf.stx_mask |= Stat.STATX_BLOCKS;
            }
            if (BitTest.test(mask, Stat.STATX_ATIME)) {
                buf.stx_atime = new StatxTimestamp(new Date(((FileTime) attrs.get("lastAccessTime")).toMillis()));
                buf.stx_mask |= Stat.STATX_ATIME;
            }
            if (BitTest.test(mask, Stat.STATX_MTIME)) {
                buf.stx_mtime = new StatxTimestamp(new Date(((FileTime) attrs.get("lastModifiedTime")).toMillis()));
                buf.stx_mask |= Stat.STATX_MTIME;
            }
            if (BitTest.test(mask, Stat.STATX_CTIME)) {
                buf.stx_ctime = new StatxTimestamp(new Date(((FileTime) attrs.get("ctime")).toMillis()));
                buf.stx_mask |= Stat.STATX_CTIME;
            }
        } catch (IOException e) {
            throw new PosixException(Errno.EIO);
        } catch (UnsupportedOperationException e) {
            try {
                BasicFileAttributes info = Files.getFileAttributeView(path,
                                BasicFileAttributeView.class, NOFOLLOW_LINKS).readAttributes();
                File f = path.toFile();
                if (BitTest.test(mask, Stat.STATX_INO)) {
                    buf.stx_ino = 1;
                    buf.stx_mask |= Stat.STATX_INO;
                }
                if (BitTest.test(mask, Stat.STATX_TYPE) || BitTest.test(mask, Stat.STATX_MODE)) {
                    if (info.isRegularFile()) {
                        buf.stx_mode = (short) Stat.S_IFREG;
                    } else if (info.isSymbolicLink()) {
                        buf.stx_mode = (short) Stat.S_IFLNK;
                    } else if (info.isDirectory()) {
                        buf.stx_mode = Stat.S_IFDIR;
                    } else {
                        buf.stx_mode = Stat.S_IFIFO;
                    }
                    if (f.canRead()) {
                        buf.stx_mode |= Stat.S_IRUSR | Stat.S_IRGRP | Stat.S_IROTH;
                    }
                    if (f.canWrite()) {
                        buf.stx_mode |= Stat.S_IWUSR | Stat.S_IWGRP | Stat.S_IWOTH;
                    }
                    if (f.canExecute()) {
                        buf.stx_mode |= Stat.S_IXUSR | Stat.S_IXGRP | Stat.S_IXOTH;
                    }
                    buf.stx_mask |= Stat.STATX_TYPE | Stat.STATX_MODE;
                }
                if (BitTest.test(mask, Stat.STATX_NLINK)) {
                    buf.stx_nlink = 1;
                    buf.stx_mask |= Stat.STATX_NLINK;
                }
                if (BitTest.test(mask, Stat.STATX_UID)) {
                    buf.stx_uid = 0;
                    buf.stx_mask |= Stat.STATX_UID;
                }
                if (BitTest.test(mask, Stat.STATX_GID)) {
                    buf.stx_gid = 0;
                    buf.stx_mask |= Stat.STATX_GID;
                }
                if (BitTest.test(mask, Stat.STATX_SIZE)) {
                    buf.stx_size = info.size();
                    buf.stx_mask |= Stat.STATX_SIZE;
                }
                if (BitTest.test(mask, Stat.STATX_BLOCKS)) {
                    buf.stx_blksize = 4096;
                    buf.stx_blocks = (long) Math.ceil(buf.stx_size / 512.0);
                    buf.stx_mask |= Stat.STATX_BLOCKS;
                }
                if (BitTest.test(mask, Stat.STATX_ATIME)) {
                    buf.stx_atime = new StatxTimestamp(new Date(info.lastAccessTime().toMillis()));
                    buf.stx_mask |= Stat.STATX_ATIME;
                }
                if (BitTest.test(mask, Stat.STATX_MTIME)) {
                    buf.stx_mtime = new StatxTimestamp(new Date(info.lastModifiedTime().toMillis()));
                    buf.stx_mask |= Stat.STATX_MTIME;
                }
                if (BitTest.test(mask, Stat.STATX_CTIME)) {
                    buf.stx_ctime = new StatxTimestamp(new Date(info.lastModifiedTime().toMillis()));
                    buf.stx_mask |= Stat.STATX_CTIME;
                }
            } catch (IOException ex) {
                throw new PosixException(Errno.EIO);
            }
        }
    }

    @Override
    public void stat(Stat buf) throws PosixException {
        stat(absolutePath, buf);
    }

    @Override
    public void statx(int mask, Statx buf) throws PosixException {
        statx(absolutePath, mask, buf);
    }
}
