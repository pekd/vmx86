package org.graalvm.vm.x86.emu;

public class Syscalls {
    public static final int SYS_read = 0;
    public static final int SYS_write = 1;
    public static final int SYS_open = 2;
    public static final int SYS_close = 3;
    public static final int SYS_stat = 4;
    public static final int SYS_fstat = 5;
    public static final int SYS_lstat = 6;
    public static final int SYS_lseek = 8;
    public static final int SYS_mmap = 9;
    public static final int SYS_mprotect = 10;
    public static final int SYS_munmap = 11;
    public static final int SYS_brk = 12;
    public static final int SYS_rt_sigaction = 13;
    public static final int SYS_rt_sigprocmask = 14;
    public static final int SYS_ioctl = 16;
    public static final int SYS_pread64 = 17;
    public static final int SYS_pwrite64 = 18;
    public static final int SYS_readv = 19;
    public static final int SYS_writev = 20;
    public static final int SYS_access = 21;
    public static final int SYS_dup = 32;
    public static final int SYS_dup2 = 33;
    public static final int SYS_getpid = 39;
    public static final int SYS_exit = 60;
    public static final int SYS_uname = 63;
    public static final int SYS_fcntl = 72;
    public static final int SYS_fsync = 74;
    public static final int SYS_getdents = 78;
    public static final int SYS_getcwd = 79;
    public static final int SYS_creat = 85;
    public static final int SYS_unlink = 87;
    public static final int SYS_readlink = 89;
    public static final int SYS_gettimeofday = 96;
    public static final int SYS_sysinfo = 99;
    public static final int SYS_times = 100;
    public static final int SYS_getuid = 102;
    public static final int SYS_getgid = 104;
    public static final int SYS_setuid = 105;
    public static final int SYS_setgid = 106;
    public static final int SYS_geteuid = 107;
    public static final int SYS_getegid = 108;
    public static final int SYS_arch_prctl = 158;
    public static final int SYS_gettid = 186;
    public static final int SYS_time = 201;
    public static final int SYS_clock_gettime = 228;
    public static final int SYS_exit_group = 231;
    public static final int SYS_tgkill = 234;
    public static final int SYS_openat = 257;
    public static final int SYS_dup3 = 292;
    public static final int SYS_prlimit64 = 302;

    // non-standard VM only syscalls
    public static final int SYS_DEBUG = 0xDEADBEEF;
    public static final int SYS_PRINTK = 0xDEADBABE;
}