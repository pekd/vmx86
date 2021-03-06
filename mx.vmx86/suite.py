#
# Copyright (c) 2019, Oracle and/or its affiliates. All rights reserved.
# DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
#
# The Universal Permissive License (UPL), Version 1.0
#
# Subject to the condition set forth below, permission is hereby granted to any
# person obtaining a copy of this software, associated documentation and/or
# data (collectively the "Software"), free of charge and under any and all
# copyright rights in the Software, and any and all patent rights owned or
# freely licensable by each licensor hereunder covering either (i) the
# unmodified Software as contributed to or provided by such licensor, or (ii)
# the Larger Works (as defined below), to deal in both
#
# (a) the Software, and
#
# (b) any piece of software and/or hardware listed in the lrgrwrks.txt file if
# one is included with the Software each a "Larger Work" to which the Software
# is contributed by such licensors),
#
# without restriction, including without limitation the rights to copy, create
# derivative works of, display, perform, and distribute the Software and make,
# use, sell, offer for sale, import, export, have made, and have sold the
# Software and the Larger Work(s), and to sublicense the foregoing rights on
# either these or other terms.
#
# This license is subject to the following condition:
#
# The above copyright notice and either this complete permission notice or at a
# minimum a reference to the UPL must be included in all copies or substantial
# portions of the Software.
#
# THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
# IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
# FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
# AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
# LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
# OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
# SOFTWARE.
#
suite = {
  "mxversion" : "5.223.0",
  "name" : "vmx86",
  "versionConflictResolution" : "latest",

  "imports" : {
    "suites" : [
      {
        "name" : "truffle",
        "subdir" : True,
        "version" : "00e0018db91b66ebaec939f6cd7fa5f90f46ef30",
        "urls" : [
          {"url" : "https://github.com/oracle/graal", "kind" : "git"},
          {"url" : "https://curio.ssw.jku.at/nexus/content/repositories/snapshots", "kind" : "binary"},
        ]
      },
      {
        "name" : "substratevm",
        "subdir" : True,
        "version" : "00e0018db91b66ebaec939f6cd7fa5f90f46ef30",
        "urls" : [
          {"url" : "https://github.com/oracle/graal", "kind" : "git"},
          {"url" : "https://curio.ssw.jku.at/nexus/content/repositories/snapshots", "kind" : "binary"},
        ]
      }
    ]
  },

  "javac.lint.overrides" : "none",

  "licenses" : {
    "UPL" : {
      "name" : "Universal Permissive License, Version 1.0",
      "url" : "http://opensource.org/licenses/UPL",
    },
    "LGPL-2.1" : {
      "name" : "GNU Lesser General Public License version 2.1",
      "url" : "http://opensource.org/licenses/LGPL-2.1",
    }
  },

  "libraries" : {
    "XED" : {
      # source code: https://github.com/intelxed/xed
      "urls" : [
        "https://lafo.ssw.jku.at/pub/graal-external-deps/xed-install-base-2018-06-29-lin-x86-64.tar.gz"
      ],
      "sha1" : "ec6ada3f0f3c8c71b57724b3d841d21d6c57fa67",
      "license" : "Apache-2.0",
    },

    "SLF4J_API" : {
      "sha1" : "b5a4b6d16ab13e34a88fae84c35cd5d68cac922c",
      "maven" : {
        "groupId" : "org.slf4j",
        "artifactId" : "slf4j-api",
        "version" : "1.7.30",
      },
    },

    "SLF4J_JDK14" : {
      "sha1" : "d35953dd2fe54ebe39fdf18cfd82fe6eb35b25ed",
      "maven" : {
        "groupId" : "org.slf4j",
        "artifactId" : "slf4j-jdk14",
        "version" : "1.7.30",
      },
    },

    "TYPESAFE_CONFIG" : {
      "sha1" : "a8b341fe81552834edc231193afd6f56a96f0eff",
      "maven" : {
        "groupId" : "com.typesafe",
        "artifactId" : "config",
        "version" : "1.4.0",
      },
    },

    "NETTY" : {
      "sha1" : "e830eae36d22f2bba3118a3bc08e17f15263a01d",
      "maven" : {
        "groupId" : "io.netty",
        "artifactId" : "netty-all",
        "version" : "4.1.45.Final",
      },
    },

    "CASSANDRA_NATIVE_PROTOCOL" : {
      "sha1" : "c9ac03b10935050ad6edcb082142de4664c8d1d9",
      "maven" : {
        "groupId" : "com.datastax.oss",
        "artifactId" : "native-protocol",
        "version" : "1.4.8",
      },
    },

    "CASSANDRA_GUAVA" : {
      "sha1" : "5aefe7f72452c5c6d7532bfe954814ad80512b46",
      "maven" : {
        "groupId" : "com.datastax.oss",
        "artifactId" : "java-driver-shaded-guava",
        "version" : "25.1-jre",
      },
    },

    "CASSANDRA" : {
      "sha1" : "7a141a50b05ae6961c618786d11768c62cfa7e2d",
      "maven" : {
        "groupId" : "com.datastax.oss",
        "artifactId" : "java-driver-core",
        "version" : "4.4.0",
      },
      "dependencies" : ["CASSANDRA_NATIVE_PROTOCOL", "CASSANDRA_GUAVA", "NETTY", "TYPESAFE_CONFIG", "SLF4J_API", "SLF4J_JDK14"]
    },

    "DERBY" : {
      "sha1" : "7efad40ef52fbb1f08142f07a83b42d29e47d8ce",
      "maven" : {
        "groupId" : "org.apache.derby",
        "artifactId" : "derby",
        "version" : "10.14.2.0"
      }
    }
  },

  "defaultLicense" : "UPL",

  "projects" : {
    "org.graalvm.vm.memory" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "jniHeaders" : True,
      "dependencies" : [
        "org.graalvm.vm.util",
        "org.graalvm.vm.posix",
        "truffle:TRUFFLE_API",
      ],
      "javaCompliance" : "1.8+",
      "annotationProcessors" : ["truffle:TRUFFLE_DSL_PROCESSOR"],
      "workingSets" : "vmx86",
      "license" : "UPL",
    },

    "org.graalvm.vm.memory.svm" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "dependencies" : [
        "org.graalvm.vm.memory",
        "substratevm:SVM"
      ],
      "javaCompliance" : "1.8+",
      "annotationProcessors" : ["truffle:TRUFFLE_DSL_PROCESSOR"],
      "workingSets" : "vmx86",
      "license" : "UPL",
    },

    "org.graalvm.vm.memory.native" : {
      "subDir" : "projects",
      "native" : True,
      "vpath" : True,
      "results" : [
        "bin/<lib:memory>",
        "bin/libmemory.a"
      ],
      "buildDependencies" : [
        "org.graalvm.vm.memory",
        "XED"
      ],
      "buildEnv" : {
        "CFLAGS" : "-I<jnigen:org.graalvm.vm.memory>",
        "LIBMEMORY" : "<lib:memory>",
        "XED" : "<path:XED>",
        "XED_VERSION" : "xed-install-base-2018-06-29-lin-x86-64",
        "OS" : "<os>",
        "ARCH" : "<arch>"
      },
      "license" : "UPL",
    },

    "org.graalvm.vm.x86" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "dependencies" : [
        "org.graalvm.vm.memory",
        "org.graalvm.vm.math",
        "truffle:TRUFFLE_API",
      ],
      "javaCompliance" : "1.8+",
      "annotationProcessors" : ["truffle:TRUFFLE_DSL_PROCESSOR"],
      "workingSets" : "vmx86",
      "license" : "UPL",
    },

    "org.graalvm.vm.x86.nfi" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "dependencies" : [
        "org.graalvm.vm.x86",
        "truffle:TRUFFLE_NFI"
      ],
      "javaCompliance" : "1.8+",
      "annotationProcessors" : ["truffle:TRUFFLE_DSL_PROCESSOR"],
      "workingSets" : "vmx86",
      "license" : "UPL",
    },

    "org.graalvm.vm.x86.launcher" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "dependencies" : [
        "org.graalvm.vm.util",
        "sdk:LAUNCHER_COMMON"
      ],
      "javaCompliance" : "1.8+",
      "workingSets" : "vmx86",
      "license" : "UPL",
    },

    "org.graalvm.vm.trcview.arch.x86" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "dependencies" : [
        "org.graalvm.vm.x86",
        "org.graalvm.vm.trcview",
        "org.graalvm.vm.trcview.libtrc",
      ],
      "javaCompliance" : "1.8+",
      "workingSets" : "vmx86",
      "license" : "UPL",
    },

    "org.graalvm.vm.trcview.arch.pdp11" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "dependencies" : [
        "org.graalvm.vm.trcview",
      ],
      "javaCompliance" : "1.8+",
      "workingSets" : "vmx86",
      "license" : "UPL",
    },

    "org.graalvm.vm.trcview.arch.custom" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "dependencies" : [
        "org.graalvm.vm.trcview",
      ],
      "javaCompliance" : "1.8+",
      "workingSets" : "vmx86",
      "license" : "UPL",
    },

    "org.graalvm.vm.trcview.storage.cassandra" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "dependencies" : [
        "org.graalvm.vm.trcview",
        "CASSANDRA"
      ],
      "javaCompliance" : "1.8+",
      "workingSets" : "vmx86",
      "license" : "UPL",
    },

    "org.graalvm.vm.trcview" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "dependencies" : [
        "org.graalvm.vm.posix",
        "org.graalvm.vm.util",
        "org.graalvm.vm.memory",
      ],
      "javaCompliance" : "1.8+",
      "workingSets" : "vmx86",
      "license" : "UPL",
    },

    "org.graalvm.vm.trcview.libtrc" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "dependencies" : [
        "org.graalvm.vm.util",
        "org.graalvm.vm.posix",
      ],
      "javaCompliance" : "1.8+",
      "workingSets" : "vmx86",
      "license" : "UPL",
    },

    "org.graalvm.vm.x86.testcases.asm" : {
      "subDir" : "tests",
      "class" : "VMX86TestSuite",
      "testProject" : True,
      "license" : "UPL",
    },

    "org.graalvm.vm.x86.testcases.c" : {
      "subDir" : "tests",
      "class" : "VMX86TestSuite",
      "testProject" : True,
      "license" : "UPL",
    },

    "org.graalvm.vm.x86.test" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "dependencies" : [
        "org.graalvm.vm.x86",
        "org.graalvm.vm.x86.testcases.asm",
        "org.graalvm.vm.x86.testcases.c",
        "mx:JUNIT",
      ],
      "javaCompliance" : "1.8+",
      "workingSets" : "vmx86",
      "testProject" : True,
      "license" : "UPL",
    },

    "org.graalvm.vm.memory.test" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "dependencies" : [
        "org.graalvm.vm.memory",
        "mx:JUNIT",
      ],
      "javaCompliance" : "1.8+",
      "workingSets" : "vmx86",
      "testProject" : True,
      "license" : "UPL",
    },

    "org.graalvm.vm.trcview.test" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "dependencies" : [
        "org.graalvm.vm.trcview",
        "mx:JUNIT",
      ],
      "javaCompliance" : "1.8+",
      "workingSets" : "vmx86",
      "testProject" : True,
      "license" : "UPL",
    },

    "org.graalvm.vm.trcview.arch.custom.test" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "dependencies" : [
        "org.graalvm.vm.trcview.arch.custom",
        "mx:JUNIT",
      ],
      "javaCompliance" : "1.8+",
      "workingSets" : "vmx86",
      "testProject" : True,
      "license" : "UPL",
    },

    "org.graalvm.vm.x86.emu" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "jniHeaders" : True,
      "dependencies" : [
        "org.graalvm.vm.memory",
        "org.graalvm.vm.x86"
      ],
      "javaCompliance" : "1.8+",
      "workingSets" : "vmx86",
      "testProject" : True,
      "license" : "UPL",
    },

    "org.graalvm.vm.x86.emu.native" : {
      "subDir" : "projects",
      "native" : True,
      "vpath" : True,
      "results" : [
        "bin/<lib:emu86>",
      ],
      "buildDependencies" : [
        "org.graalvm.vm.x86.emu",
      ],
      "buildEnv" : {
        "CFLAGS" : "-I<jnigen:org.graalvm.vm.x86.emu>",
        "LIBEMU86" : "<lib:emu86>",
        "OS" : "<os>",
      },
      "testProject" : True,
      "license" : "UPL",
    },

    "org.graalvm.vm.x86.nfi.native" : {
      "subDir" : "projects",
      "native" : True,
      "vpath" : True,
      "results" : [
        "bin/<lib:nfi>",
      ],
      "buildEnv" : {
        "LIBNFI" : "<lib:nfi>",
        "OS" : "<os>",
      },
      "license" : "UPL",
    },

    "org.graalvm.vm.util" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "dependencies" : [
        "truffle:TRUFFLE_API",
      ],
      "javaCompliance" : "1.8+",
      "workingSets" : "core",
      "license" : "UPL",
    },

    "org.graalvm.vm.math" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "dependencies" : [
        "truffle:TRUFFLE_API",
      ],
      "javaCompliance" : "1.8+",
      "workingSets" : "core",
      "license" : "UPL",
    },

    "org.graalvm.vm.posix" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "dependencies" : [
        "org.graalvm.vm.util",
        "truffle:TRUFFLE_API",
      ],
      "javaCompliance" : "1.8+",
      "workingSets" : "core",
      "license" : "UPL",
    },

    "org.graalvm.vm.util.test" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "dependencies" : [
        "org.graalvm.vm.util",
        "mx:JUNIT",
      ],
      "javaCompliance" : "1.8+",
      "workingSets" : "core",
      "license" : "UPL",
    },

    "org.graalvm.vm.math.test" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "dependencies" : [
        "org.graalvm.vm.math",
        "mx:JUNIT",
      ],
      "javaCompliance" : "1.8+",
      "workingSets" : "core",
      "license" : "UPL",
    },

    "org.graalvm.vm.posix.test" : {
      "subDir" : "projects",
      "sourceDirs" : ["src"],
      "dependencies" : [
        "org.graalvm.vm.posix",
        "mx:JUNIT",
      ],
      "javaCompliance" : "1.8+",
      "workingSets" : "core",
      "license" : "UPL",
    },
  },

  "distributions" : {
    "VM" : {
      "path" : "build/vmx86.jar",
      "subDir" : "vmx86",
      "sourcesPath" : "build/vmx86.src.zip",
      "dependencies" : [
        "org.graalvm.vm.x86",
      ],
      "distDependencies" : [
        "CORE",
        "POSIX",
        "truffle:TRUFFLE_API",
      ],
      "license" : "UPL",
    },

    "VM_MEMORY_NATIVE" : {
      "native" : True,
      "platformDependent" : True,
      "output" : "build",
      "dependencies" : [
        "org.graalvm.vm.memory.native"
      ],
      "license" : "UPL",
    },

    "VM_MEMORY_SVM" : {
      "path" : "build/memory-svm.jar",
      "subDir" : "vmx86",
      "sourcesPath" : "build/memory-svm.src.zip",
      "dependencies" : [
        "org.graalvm.vm.memory.svm",
        "substratevm:SVM"
      ],
      "distDependencies" : [
        "VM"
      ],
      "license" : "UPL",
    },

    "VM_NFI" : {
      "path" : "build/vmx86_nfi.jar",
      "subDir" : "vmx86",
      "sourcesPath" : "build/vmx86_nfi.src.zip",
      "dependencies" : [
        "org.graalvm.vm.x86.nfi"
      ],
      "distDependencies" : [
        "VM",
        "VM_NFI_NATIVE",
        "CORE",
        "POSIX",
        "truffle:TRUFFLE_API",
        "truffle:TRUFFLE_NFI",
      ],
      "license" : "UPL",
    },

    "VM_NFI_NATIVE" : {
      "native" : True,
      "platformDependent" : True,
      "output" : "build",
      "dependencies" : [
        "org.graalvm.vm.x86.nfi.native"
      ],
      "license" : "UPL",
    },

    "VMX86_LAUNCHER" : {
      "path" : "build/vmx86-launcher.jar",
      "sourcesPath" : "build/vmx86-launcher.src.zip",
      "subDir" : "vmx86",
      "mainClass" : "org.graalvm.vm.x86.launcher.AMD64Launcher",
      "dependencies" : ["org.graalvm.vm.x86.launcher"],
      "distDependencies" : [
        "CORE",
        "sdk:LAUNCHER_COMMON",
      ],
      "license" : "UPL",
    },

    "VMX86_TRCVIEW" : {
      "path" : "build/trcview.jar",
      "sourcesPath" : "build/trcview.src.zip",
      "subDir" : "vmx86",
      "mainClass" : "org.graalvm.vm.trcview.ui.MainWindow",
      "dependencies" : [
        "org.graalvm.vm.trcview",
        "org.graalvm.vm.trcview.arch.x86",
        "org.graalvm.vm.trcview.arch.pdp11",
        "org.graalvm.vm.trcview.arch.custom",
      ],
      "strip" : [
        "trcview"
      ],
      "overlaps" : [
        "VM",
        "CORE",
        "POSIX",
        "sdk:GRAAL_SDK",
        "truffle:TRUFFLE_API",
        "truffle:TRUFFLE_NFI",
      ],
      "license" : "UPL",
    },

    "LIBTRC" : {
      "path" : "build/libtrc.jar",
      "subDir" : "core",
      "sourcesPath" : "build/libtrc.src.zip",
      "dependencies" : [
        "org.graalvm.vm.trcview.libtrc",
      ],
      "distDependencies" : [
        "CORE",
        "POSIX",
      ],
      "license" : "UPL",
    },

    "VM_TESTCASES" : {
      "native" : True,
      "platformDependent" : True,
      "output" : "mxbuild/testcases",
      "dependencies" : [
        "org.graalvm.vm.x86.testcases.asm",
        "org.graalvm.vm.x86.testcases.c",
      ],
      "javaProperties" : {
        "library.path" : "<path:VM_MEMORY_NATIVE>/<lib:memory>"
      },
      "license" : "UPL",
    },

    "VM_TEST" : {
      "path" : "build/vmx86_test.jar",
      "subDir" : "vmx86",
      "sourcesPath" : "build/vmx86_test.src.zip",
      "dependencies" : [
        "org.graalvm.vm.x86.test",
        "org.graalvm.vm.memory.test"
      ],
      "distDependencies" : [
        "VM",
        "VM_NFI",
        "VM_TESTCASES",
        "CORE",
        "POSIX",
        "truffle:TRUFFLE_API",
        "truffle:TRUFFLE_NFI",
        "truffle:TRUFFLE_TCK",
        "sdk:POLYGLOT_TCK"
      ],
      "exclude" : [
        "mx:JUNIT"
      ],
      "javaProperties" : {
        "vmx86test.testSuitePath" : "<path:VM_TESTCASES>"
      },
      "license" : "UPL",
    },

    "VM_EMU86_NATIVE" : {
      "native" : True,
      "platformDependent" : True,
      "output" : "build",
      "dependencies" : [
        "org.graalvm.vm.x86.emu.native"
      ],
      "license" : "UPL",
    },

    "VM_EMU86" : {
      "path" : "build/vmx86emu.jar",
      "subDir" : "vmx86",
      "sourcesPath" : "build/vmx86emu.src.zip",
      "dependencies" : [
        "org.graalvm.vm.x86.emu"
      ],
      "distDependencies" : [
        "VM",
        "CORE",
        "POSIX"
      ],
      "license" : "UPL",
    },

    "VMX86_GRAALVM_SUPPORT" : {
      "native" : True,
      "platformDependent" : True,
      "description" : "vmx86 support distribution for the GraalVM",
      "layout": {
        "native-image.properties" : "file:mx.vmx86/native-image.properties",
        "./" : ["extracted-dependency:vmx86:VM_MEMORY_NATIVE/<lib:memory>", "extracted-dependency:vmx86:VM_NFI_NATIVE/<lib:nfi>"],
        "clibraries/<os>-<arch>/" : ["extracted-dependency:vmx86:VM_MEMORY_NATIVE/libmemory.a", "file:projects/org.graalvm.vm.memory.native/include"],
      },
      "license" : "UPL",
    },

    "CORE" : {
      "path" : "build/core.jar",
      "subDir" : "core",
      "sourcesPath" : "build/core.src.zip",
      "dependencies" : [
        "org.graalvm.vm.util",
        "org.graalvm.vm.math",
      ],
      "distDependencies" : [
        "truffle:TRUFFLE_API",
      ],
      "license" : "UPL",
    },

    "POSIX" : {
      "path" : "build/posix.jar",
      "subDir" : "core",
      "sourcesPath" : "build/posix.src.zip",
      "dependencies" : [
        "org.graalvm.vm.posix",
      ],
      "distDependencies" : [
        "CORE",
        "truffle:TRUFFLE_API"
      ],
      "license" : "UPL",
    },

    "CORE_TEST" : {
      "path" : "build/core_test.jar",
      "subDir" : "core",
      "sourcesPath" : "build/core_test.src.zip",
      "dependencies" : [
        "org.graalvm.vm.util.test",
        "org.graalvm.vm.math.test",
      ],
      "exclude" : [
        "mx:JUNIT"
      ],
      "distDependencies" : [
        "CORE",
      ],
      "license" : "UPL",
    },

    "POSIX_TEST" : {
      "path" : "build/posix_test.jar",
      "subDir" : "core",
      "sourcesPath" : "build/posix_test.src.zip",
      "dependencies" : [
        "org.graalvm.vm.posix.test"
      ],
      "exclude" : [
        "mx:JUNIT"
      ],
      "distDependencies" : [
        "CORE",
        "POSIX"
      ],
      "license" : "UPL",
    }
  }
}
