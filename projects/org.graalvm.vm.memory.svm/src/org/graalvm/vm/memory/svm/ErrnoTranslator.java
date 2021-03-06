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
package org.graalvm.vm.memory.svm;

import static com.oracle.svm.core.headers.Errno.E2BIG;
import static com.oracle.svm.core.headers.Errno.EACCES;
import static com.oracle.svm.core.headers.Errno.EADDRINUSE;
import static com.oracle.svm.core.headers.Errno.EADDRNOTAVAIL;
import static com.oracle.svm.core.headers.Errno.EAFNOSUPPORT;
import static com.oracle.svm.core.headers.Errno.EAGAIN;
import static com.oracle.svm.core.headers.Errno.EALREADY;
import static com.oracle.svm.core.headers.Errno.EBADF;
import static com.oracle.svm.core.headers.Errno.EBADMSG;
import static com.oracle.svm.core.headers.Errno.EBUSY;
import static com.oracle.svm.core.headers.Errno.ECANCELED;
import static com.oracle.svm.core.headers.Errno.ECHILD;
import static com.oracle.svm.core.headers.Errno.ECONNABORTED;
import static com.oracle.svm.core.headers.Errno.ECONNREFUSED;
import static com.oracle.svm.core.headers.Errno.ECONNRESET;
import static com.oracle.svm.core.headers.Errno.EDEADLK;
import static com.oracle.svm.core.headers.Errno.EDESTADDRREQ;
import static com.oracle.svm.core.headers.Errno.EDOM;
import static com.oracle.svm.core.headers.Errno.EDQUOT;
import static com.oracle.svm.core.headers.Errno.EEXIST;
import static com.oracle.svm.core.headers.Errno.EFAULT;
import static com.oracle.svm.core.headers.Errno.EFBIG;
import static com.oracle.svm.core.headers.Errno.EHOSTDOWN;
import static com.oracle.svm.core.headers.Errno.EHOSTUNREACH;
import static com.oracle.svm.core.headers.Errno.EIDRM;
import static com.oracle.svm.core.headers.Errno.EILSEQ;
import static com.oracle.svm.core.headers.Errno.EINPROGRESS;
import static com.oracle.svm.core.headers.Errno.EINTR;
import static com.oracle.svm.core.headers.Errno.EINVAL;
import static com.oracle.svm.core.headers.Errno.EIO;
import static com.oracle.svm.core.headers.Errno.EISCONN;
import static com.oracle.svm.core.headers.Errno.EISDIR;
import static com.oracle.svm.core.headers.Errno.ELOOP;
import static com.oracle.svm.core.headers.Errno.EMFILE;
import static com.oracle.svm.core.headers.Errno.EMLINK;
import static com.oracle.svm.core.headers.Errno.EMSGSIZE;
import static com.oracle.svm.core.headers.Errno.EMULTIHOP;
import static com.oracle.svm.core.headers.Errno.ENAMETOOLONG;
import static com.oracle.svm.core.headers.Errno.ENETDOWN;
import static com.oracle.svm.core.headers.Errno.ENETRESET;
import static com.oracle.svm.core.headers.Errno.ENETUNREACH;
import static com.oracle.svm.core.headers.Errno.ENFILE;
import static com.oracle.svm.core.headers.Errno.ENOBUFS;
import static com.oracle.svm.core.headers.Errno.ENODEV;
import static com.oracle.svm.core.headers.Errno.ENOENT;
import static com.oracle.svm.core.headers.Errno.ENOEXEC;
import static com.oracle.svm.core.headers.Errno.ENOLCK;
import static com.oracle.svm.core.headers.Errno.ENOMEM;
import static com.oracle.svm.core.headers.Errno.ENOMSG;
import static com.oracle.svm.core.headers.Errno.ENOPROTOOPT;
import static com.oracle.svm.core.headers.Errno.ENOSPC;
import static com.oracle.svm.core.headers.Errno.ENOSR;
import static com.oracle.svm.core.headers.Errno.ENOSYS;
import static com.oracle.svm.core.headers.Errno.ENOTBLK;
import static com.oracle.svm.core.headers.Errno.ENOTCONN;
import static com.oracle.svm.core.headers.Errno.ENOTDIR;
import static com.oracle.svm.core.headers.Errno.ENOTEMPTY;
import static com.oracle.svm.core.headers.Errno.ENOTRECOVERABLE;
import static com.oracle.svm.core.headers.Errno.ENOTSOCK;
import static com.oracle.svm.core.headers.Errno.ENOTTY;
import static com.oracle.svm.core.headers.Errno.ENXIO;
import static com.oracle.svm.core.headers.Errno.EOPNOTSUPP;
import static com.oracle.svm.core.headers.Errno.EOVERFLOW;
import static com.oracle.svm.core.headers.Errno.EOWNERDEAD;
import static com.oracle.svm.core.headers.Errno.EPERM;
import static com.oracle.svm.core.headers.Errno.EPFNOSUPPORT;
import static com.oracle.svm.core.headers.Errno.EPIPE;
import static com.oracle.svm.core.headers.Errno.EPROTO;
import static com.oracle.svm.core.headers.Errno.EPROTONOSUPPORT;
import static com.oracle.svm.core.headers.Errno.EPROTOTYPE;
import static com.oracle.svm.core.headers.Errno.ERANGE;
import static com.oracle.svm.core.headers.Errno.EREMOTE;
import static com.oracle.svm.core.headers.Errno.EROFS;
import static com.oracle.svm.core.headers.Errno.ESHUTDOWN;
import static com.oracle.svm.core.headers.Errno.ESOCKTNOSUPPORT;
import static com.oracle.svm.core.headers.Errno.ESPIPE;
import static com.oracle.svm.core.headers.Errno.ESRCH;
import static com.oracle.svm.core.headers.Errno.ESTALE;
import static com.oracle.svm.core.headers.Errno.ETIME;
import static com.oracle.svm.core.headers.Errno.ETIMEDOUT;
import static com.oracle.svm.core.headers.Errno.ETOOMANYREFS;
import static com.oracle.svm.core.headers.Errno.ETXTBSY;
import static com.oracle.svm.core.headers.Errno.EUSERS;
import static com.oracle.svm.core.headers.Errno.EWOULDBLOCK;
import static com.oracle.svm.core.headers.Errno.EXDEV;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.graalvm.vm.posix.api.Errno;
import org.graalvm.vm.util.log.Trace;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;

public class ErrnoTranslator {
    private static final Logger log = Trace.create(ErrnoTranslator.class);

    private static final Map<Integer, Integer> ERRORS = new HashMap<>();
    private static boolean initialized = false;

    private static void init() {
        ERRORS.put(EPERM(), Errno.EPERM);
        ERRORS.put(ENOENT(), Errno.ENOENT);
        ERRORS.put(ESRCH(), Errno.ESRCH);
        ERRORS.put(EINTR(), Errno.EINTR);
        ERRORS.put(EIO(), Errno.EIO);
        ERRORS.put(ENXIO(), Errno.ENXIO);
        ERRORS.put(E2BIG(), Errno.E2BIG);
        ERRORS.put(ENOEXEC(), Errno.ENOEXEC);
        ERRORS.put(EBADF(), Errno.EBADF);
        ERRORS.put(ECHILD(), Errno.ECHILD);
        ERRORS.put(EAGAIN(), Errno.EAGAIN);
        ERRORS.put(ENOMEM(), Errno.ENOMEM);
        ERRORS.put(EACCES(), Errno.EACCES);
        ERRORS.put(EFAULT(), Errno.EFAULT);
        ERRORS.put(ENOTBLK(), Errno.ENOTBLK);
        ERRORS.put(EBUSY(), Errno.EBUSY);
        ERRORS.put(EEXIST(), Errno.EEXIST);
        ERRORS.put(EXDEV(), Errno.EXDEV);
        ERRORS.put(ENODEV(), Errno.ENODEV);
        ERRORS.put(ENOTDIR(), Errno.ENOTDIR);
        ERRORS.put(EISDIR(), Errno.EISDIR);
        ERRORS.put(EINVAL(), Errno.EINVAL);
        ERRORS.put(ENFILE(), Errno.ENFILE);
        ERRORS.put(EMFILE(), Errno.EMFILE);
        ERRORS.put(ENOTTY(), Errno.ENOTTY);
        ERRORS.put(ETXTBSY(), Errno.ETXTBSY);
        ERRORS.put(EFBIG(), Errno.EFBIG);
        ERRORS.put(ENOSPC(), Errno.ENOSPC);
        ERRORS.put(ESPIPE(), Errno.ESPIPE);
        ERRORS.put(EROFS(), Errno.EROFS);
        ERRORS.put(EMLINK(), Errno.EMLINK);
        ERRORS.put(EPIPE(), Errno.EPIPE);
        ERRORS.put(EDOM(), Errno.EDOM);
        ERRORS.put(ERANGE(), Errno.ERANGE);
        ERRORS.put(EDEADLK(), Errno.EDEADLK);
        ERRORS.put(ENAMETOOLONG(), Errno.ENAMETOOLONG);
        ERRORS.put(ENOLCK(), Errno.ENOLCK);
        ERRORS.put(ENOSYS(), Errno.ENOSYS);
        ERRORS.put(ENOTEMPTY(), Errno.ENOTEMPTY);
        ERRORS.put(ELOOP(), Errno.ELOOP);
        ERRORS.put(EWOULDBLOCK(), Errno.EWOULDBLOCK);
        ERRORS.put(ENOMSG(), Errno.ENOMSG);
        ERRORS.put(EIDRM(), Errno.EIDRM);
        ERRORS.put(ETIME(), Errno.ETIME);
        ERRORS.put(ENOSR(), Errno.ENOSR);
        ERRORS.put(EREMOTE(), Errno.EREMOTE);
        ERRORS.put(EPROTO(), Errno.EPROTO);
        ERRORS.put(EMULTIHOP(), Errno.EMULTIHOP);
        ERRORS.put(EBADMSG(), Errno.EBADMSG);
        ERRORS.put(EOVERFLOW(), Errno.EOVERFLOW);
        ERRORS.put(EILSEQ(), Errno.EILSEQ);
        ERRORS.put(EUSERS(), Errno.EUSERS);
        ERRORS.put(ENOTSOCK(), Errno.ENOTSOCK);
        ERRORS.put(EDESTADDRREQ(), Errno.EDESTADDRREQ);
        ERRORS.put(EMSGSIZE(), Errno.EMSGSIZE);
        ERRORS.put(EPROTOTYPE(), Errno.EPROTOTYPE);
        ERRORS.put(ENOPROTOOPT(), Errno.ENOPROTOOPT);
        ERRORS.put(EPROTONOSUPPORT(), Errno.EPROTONOSUPPORT);
        ERRORS.put(ESOCKTNOSUPPORT(), Errno.ESOCKTNOSUPPORT);
        ERRORS.put(EOPNOTSUPP(), Errno.EOPNOTSUPP);
        ERRORS.put(EPFNOSUPPORT(), Errno.EPFNOSUPPORT);
        ERRORS.put(EAFNOSUPPORT(), Errno.EAFNOSUPPORT);
        ERRORS.put(EADDRINUSE(), Errno.EADDRINUSE);
        ERRORS.put(EADDRNOTAVAIL(), Errno.EADDRNOTAVAIL);
        ERRORS.put(ENETDOWN(), Errno.ENETDOWN);
        ERRORS.put(ENETUNREACH(), Errno.ENETUNREACH);
        ERRORS.put(ENETRESET(), Errno.ENETRESET);
        ERRORS.put(ECONNABORTED(), Errno.ECONNABORTED);
        ERRORS.put(ECONNRESET(), Errno.ECONNRESET);
        ERRORS.put(ENOBUFS(), Errno.ENOBUFS);
        ERRORS.put(EISCONN(), Errno.EISCONN);
        ERRORS.put(ENOTCONN(), Errno.ENOTCONN);
        ERRORS.put(ESHUTDOWN(), Errno.ESHUTDOWN);
        ERRORS.put(ETOOMANYREFS(), Errno.ETOOMANYREFS);
        ERRORS.put(ETIMEDOUT(), Errno.ETIMEDOUT);
        ERRORS.put(ECONNREFUSED(), Errno.ECONNREFUSED);
        ERRORS.put(EHOSTDOWN(), Errno.EHOSTDOWN);
        ERRORS.put(EHOSTUNREACH(), Errno.EHOSTUNREACH);
        ERRORS.put(EALREADY(), Errno.EALREADY);
        ERRORS.put(EINPROGRESS(), Errno.EINPROGRESS);
        ERRORS.put(ESTALE(), Errno.ESTALE);
        ERRORS.put(EDQUOT(), Errno.EDQUOT);
        ERRORS.put(ECANCELED(), Errno.ECANCELED);
        ERRORS.put(EOWNERDEAD(), Errno.EOWNERDEAD);
        ERRORS.put(ENOTRECOVERABLE(), Errno.ENOTRECOVERABLE);
    }

    @TruffleBoundary
    public static int translate(int errno) {
        if (!initialized) {
            init();
            initialized = true;
        }
        Integer err = ERRORS.get(errno);
        if (err != null) {
            return err;
        } else {
            log.warning(() -> "Unknown errno code: " + errno);
            return errno;
        }
    }
}
