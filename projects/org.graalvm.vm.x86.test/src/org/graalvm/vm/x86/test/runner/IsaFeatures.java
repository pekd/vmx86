package org.graalvm.vm.x86.test.runner;

import org.junit.Test;

public class IsaFeatures {
    @Test
    public void cpuidVendorBrand() throws Exception {
        String stdout = "Vendor: 'VMX86onGraal'\n" +
                        "Brand:  'VMX86 on Graal/Truffle'\n";
        TestRunner.run("cpuid.elf", new String[0], "", stdout, "", 0);
    }
}