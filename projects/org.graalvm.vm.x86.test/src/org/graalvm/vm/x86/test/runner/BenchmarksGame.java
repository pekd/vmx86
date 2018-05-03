package org.graalvm.vm.x86.test.runner;

import static org.junit.Assume.assumeTrue;

import org.graalvm.vm.x86.test.platform.HostTest;
import org.junit.Ignore;
import org.junit.Test;

public class BenchmarksGame {
    public static final String FASTA = ">ONE Homo sapiens alu\n" +
                    "GGCCGGGCGCGGTGGCTCACGCCTGTAATCCCAGCACTTTGGGAGGCCGAGGCGGGCGGA\n" +
                    "TCACCTGAGGTCAGGAGTTCGAGACCAGCCTGGCCAACATGGTGAAACCCCGTCTCTACT\n" +
                    "AAAAATACAAAAATTAGCCGGGCGTGGTGGCGCGCGCCTGTAATCCCAGCTACTCGGGAG\n" +
                    "GCTGAGGCAGGAGAATCGCTTGAACCCGGGAGGCGGAGGTTGCAGTGAGCCGAGATCGCG\n" +
                    "CCACTGCACTCCAGCCTGGGCGACAGAGCGAGACTCCGTCTCAAAAAGGCCGGGCGCGGT\n" +
                    "GGCTCACGCCTGTAATCCCAGCACTTTGGGAGGCCGAGGCGGGCGGATCACCTGAGGTCA\n" +
                    "GGAGTTCGAGACCAGCCTGGCCAACATGGTGAAACCCCGTCTCTACTAAAAATACAAAAA\n" +
                    "TTAGCCGGGCGTGGTGGCGCGCGCCTGTAATCCCAGCTACTCGGGAGGCTGAGGCAGGAG\n" +
                    "AATCGCTTGAACCCGGGAGGCGGAGGTTGCAGTGAGCCGAGATCGCGCCACTGCACTCCA\n" +
                    "GCCTGGGCGACAGAGCGAGACTCCGTCTCAAAAAGGCCGGGCGCGGTGGCTCACGCCTGT\n" +
                    "AATCCCAGCACTTTGGGAGGCCGAGGCGGGCGGATCACCTGAGGTCAGGAGTTCGAGACC\n" +
                    "AGCCTGGCCAACATGGTGAAACCCCGTCTCTACTAAAAATACAAAAATTAGCCGGGCGTG\n" +
                    "GTGGCGCGCGCCTGTAATCCCAGCTACTCGGGAGGCTGAGGCAGGAGAATCGCTTGAACC\n" +
                    "CGGGAGGCGGAGGTTGCAGTGAGCCGAGATCGCGCCACTGCACTCCAGCCTGGGCGACAG\n" +
                    "AGCGAGACTCCGTCTCAAAAAGGCCGGGCGCGGTGGCTCACGCCTGTAATCCCAGCACTT\n" +
                    "TGGGAGGCCGAGGCGGGCGGATCACCTGAGGTCAGGAGTTCGAGACCAGCCTGGCCAACA\n" +
                    "TGGTGAAACCCCGTCTCTACTAAAAATACAAAAATTAGCCGGGCGTGGTGGCGCGCGCCT\n" +
                    "GTAATCCCAGCTACTCGGGAGGCTGAGGCAGGAGAATCGCTTGAACCCGGGAGGCGGAGG\n" +
                    "TTGCAGTGAGCCGAGATCGCGCCACTGCACTCCAGCCTGGGCGACAGAGCGAGACTCCGT\n" +
                    "CTCAAAAAGGCCGGGCGCGGTGGCTCACGCCTGTAATCCCAGCACTTTGGGAGGCCGAGG\n" +
                    "CGGGCGGATCACCTGAGGTCAGGAGTTCGAGACCAGCCTGGCCAACATGGTGAAACCCCG\n" +
                    "TCTCTACTAAAAATACAAAAATTAGCCGGGCGTGGTGGCGCGCGCCTGTAATCCCAGCTA\n" +
                    "CTCGGGAGGCTGAGGCAGGAGAATCGCTTGAACCCGGGAGGCGGAGGTTGCAGTGAGCCG\n" +
                    "AGATCGCGCCACTGCACTCCAGCCTGGGCGACAGAGCGAGACTCCGTCTCAAAAAGGCCG\n" +
                    "GGCGCGGTGGCTCACGCCTGTAATCCCAGCACTTTGGGAGGCCGAGGCGGGCGGATCACC\n" +
                    "TGAGGTCAGGAGTTCGAGACCAGCCTGGCCAACATGGTGAAACCCCGTCTCTACTAAAAA\n" +
                    "TACAAAAATTAGCCGGGCGTGGTGGCGCGCGCCTGTAATCCCAGCTACTCGGGAGGCTGA\n" +
                    "GGCAGGAGAATCGCTTGAACCCGGGAGGCGGAGGTTGCAGTGAGCCGAGATCGCGCCACT\n" +
                    "GCACTCCAGCCTGGGCGACAGAGCGAGACTCCGTCTCAAAAAGGCCGGGCGCGGTGGCTC\n" +
                    "ACGCCTGTAATCCCAGCACTTTGGGAGGCCGAGGCGGGCGGATCACCTGAGGTCAGGAGT\n" +
                    "TCGAGACCAGCCTGGCCAACATGGTGAAACCCCGTCTCTACTAAAAATACAAAAATTAGC\n" +
                    "CGGGCGTGGTGGCGCGCGCCTGTAATCCCAGCTACTCGGGAGGCTGAGGCAGGAGAATCG\n" +
                    "CTTGAACCCGGGAGGCGGAGGTTGCAGTGAGCCGAGATCGCGCCACTGCACTCCAGCCTG\n" +
                    "GGCGACAGAGCGAGACTCCG\n" +
                    ">TWO IUB ambiguity codes\n" +
                    "cttBtatcatatgctaKggNcataaaSatgtaaaDcDRtBggDtctttataattcBgtcg\n" +
                    "tactDtDagcctatttSVHtHttKtgtHMaSattgWaHKHttttagacatWatgtRgaaa\n" +
                    "NtactMcSMtYtcMgRtacttctWBacgaaatatagScDtttgaagacacatagtVgYgt\n" +
                    "cattHWtMMWcStgttaggKtSgaYaaccWStcgBttgcgaMttBYatcWtgacaYcaga\n" +
                    "gtaBDtRacttttcWatMttDBcatWtatcttactaBgaYtcttgttttttttYaaScYa\n" +
                    "HgtgttNtSatcMtcVaaaStccRcctDaataataStcYtRDSaMtDttgttSagtRRca\n" +
                    "tttHatSttMtWgtcgtatSSagactYaaattcaMtWatttaSgYttaRgKaRtccactt\n" +
                    "tattRggaMcDaWaWagttttgacatgttctacaaaRaatataataaMttcgDacgaSSt\n" +
                    "acaStYRctVaNMtMgtaggcKatcttttattaaaaagVWaHKYagtttttatttaacct\n" +
                    "tacgtVtcVaattVMBcttaMtttaStgacttagattWWacVtgWYagWVRctDattBYt\n" +
                    "gtttaagaagattattgacVatMaacattVctgtBSgaVtgWWggaKHaatKWcBScSWa\n" +
                    "accRVacacaaactaccScattRatatKVtactatatttHttaagtttSKtRtacaaagt\n" +
                    "RDttcaaaaWgcacatWaDgtDKacgaacaattacaRNWaatHtttStgttattaaMtgt\n" +
                    "tgDcgtMgcatBtgcttcgcgaDWgagctgcgaggggVtaaScNatttacttaatgacag\n" +
                    "cccccacatYScaMgtaggtYaNgttctgaMaacNaMRaacaaacaKctacatagYWctg\n" +
                    "ttWaaataaaataRattagHacacaagcgKatacBttRttaagtatttccgatctHSaat\n" +
                    "actcNttMaagtattMtgRtgaMgcataatHcMtaBSaRattagttgatHtMttaaKagg\n" +
                    "YtaaBataSaVatactWtataVWgKgttaaaacagtgcgRatatacatVtHRtVYataSa\n" +
                    "KtWaStVcNKHKttactatccctcatgWHatWaRcttactaggatctataDtDHBttata\n" +
                    "aaaHgtacVtagaYttYaKcctattcttcttaataNDaaggaaaDYgcggctaaWSctBa\n" +
                    "aNtgctggMBaKctaMVKagBaactaWaDaMaccYVtNtaHtVWtKgRtcaaNtYaNacg\n" +
                    "gtttNattgVtttctgtBaWgtaattcaagtcaVWtactNggattctttaYtaaagccgc\n" +
                    "tcttagHVggaYtgtNcDaVagctctctKgacgtatagYcctRYHDtgBattDaaDgccK\n" +
                    "tcHaaStttMcctagtattgcRgWBaVatHaaaataYtgtttagMDMRtaataaggatMt\n" +
                    "ttctWgtNtgtgaaaaMaatatRtttMtDgHHtgtcattttcWattRSHcVagaagtacg\n" +
                    "ggtaKVattKYagactNaatgtttgKMMgYNtcccgSKttctaStatatNVataYHgtNa\n" +
                    "BKRgNacaactgatttcctttaNcgatttctctataScaHtataRagtcRVttacDSDtt\n" +
                    "aRtSatacHgtSKacYagttMHtWataggatgactNtatSaNctataVtttRNKtgRacc\n" +
                    "tttYtatgttactttttcctttaaacatacaHactMacacggtWataMtBVacRaSaatc\n" +
                    "cgtaBVttccagccBcttaRKtgtgcctttttRtgtcagcRttKtaaacKtaaatctcac\n" +
                    "aattgcaNtSBaaccgggttattaaBcKatDagttactcttcattVtttHaaggctKKga\n" +
                    "tacatcBggScagtVcacattttgaHaDSgHatRMaHWggtatatRgccDttcgtatcga\n" +
                    "aacaHtaagttaRatgaVacttagattVKtaaYttaaatcaNatccRttRRaMScNaaaD\n" +
                    "gttVHWgtcHaaHgacVaWtgttScactaagSgttatcttagggDtaccagWattWtRtg\n" +
                    "ttHWHacgattBtgVcaYatcggttgagKcWtKKcaVtgaYgWctgYggVctgtHgaNcV\n" +
                    "taBtWaaYatcDRaaRtSctgaHaYRttagatMatgcatttNattaDttaattgttctaa\n" +
                    "ccctcccctagaWBtttHtBccttagaVaatMcBHagaVcWcagBVttcBtaYMccagat\n" +
                    "gaaaaHctctaacgttagNWRtcggattNatcRaNHttcagtKttttgWatWttcSaNgg\n" +
                    "gaWtactKKMaacatKatacNattgctWtatctaVgagctatgtRaHtYcWcttagccaa\n" +
                    "tYttWttaWSSttaHcaaaaagVacVgtaVaRMgattaVcDactttcHHggHRtgNcctt\n" +
                    "tYatcatKgctcctctatVcaaaaKaaaagtatatctgMtWtaaaacaStttMtcgactt\n" +
                    "taSatcgDataaactaaacaagtaaVctaggaSccaatMVtaaSKNVattttgHccatca\n" +
                    "cBVctgcaVatVttRtactgtVcaattHgtaaattaaattttYtatattaaRSgYtgBag\n" +
                    "aHSBDgtagcacRHtYcBgtcacttacactaYcgctWtattgSHtSatcataaatataHt\n" +
                    "cgtYaaMNgBaatttaRgaMaatatttBtttaaaHHKaatctgatWatYaacttMctctt\n" +
                    "ttVctagctDaaagtaVaKaKRtaacBgtatccaaccactHHaagaagaaggaNaaatBW\n" +
                    "attccgStaMSaMatBttgcatgRSacgttVVtaaDMtcSgVatWcaSatcttttVatag\n" +
                    "ttactttacgatcaccNtaDVgSRcgVcgtgaacgaNtaNatatagtHtMgtHcMtagaa\n" +
                    "attBgtataRaaaacaYKgtRccYtatgaagtaataKgtaaMttgaaRVatgcagaKStc\n" +
                    "tHNaaatctBBtcttaYaBWHgtVtgacagcaRcataWctcaBcYacYgatDgtDHccta\n" +
                    ">THREE Homo sapiens frequency\n" +
                    "aacacttcaccaggtatcgtgaaggctcaagattacccagagaacctttgcaatataaga\n" +
                    "atatgtatgcagcattaccctaagtaattatattctttttctgactcaaagtgacaagcc\n" +
                    "ctagtgtatattaaatcggtatatttgggaaattcctcaaactatcctaatcaggtagcc\n" +
                    "atgaaagtgatcaaaaaagttcgtacttataccatacatgaattctggccaagtaaaaaa\n" +
                    "tagattgcgcaaaattcgtaccttaagtctctcgccaagatattaggatcctattactca\n" +
                    "tatcgtgtttttctttattgccgccatccccggagtatctcacccatccttctcttaaag\n" +
                    "gcctaatattacctatgcaaataaacatatattgttgaaaattgagaacctgatcgtgat\n" +
                    "tcttatgtgtaccatatgtatagtaatcacgcgactatatagtgctttagtatcgcccgt\n" +
                    "gggtgagtgaatattctgggctagcgtgagatagtttcttgtcctaatatttttcagatc\n" +
                    "gaatagcttctatttttgtgtttattgacatatgtcgaaactccttactcagtgaaagtc\n" +
                    "atgaccagatccacgaacaatcttcggaatcagtctcgttttacggcggaatcttgagtc\n" +
                    "taacttatatcccgtcgcttactttctaacaccccttatgtatttttaaaattacgttta\n" +
                    "ttcgaacgtacttggcggaagcgttattttttgaagtaagttacattgggcagactcttg\n" +
                    "acattttcgatacgactttctttcatccatcacaggactcgttcgtattgatatcagaag\n" +
                    "ctcgtgatgattagttgtcttctttaccaatactttgaggcctattctgcgaaatttttg\n" +
                    "ttgccctgcgaacttcacataccaaggaacacctcgcaacatgccttcatatccatcgtt\n" +
                    "cattgtaattcttacacaatgaatcctaagtaattacatccctgcgtaaaagatggtagg\n" +
                    "ggcactgaggatatattaccaagcatttagttatgagtaatcagcaatgtttcttgtatt\n" +
                    "aagttctctaaaatagttacatcgtaatgttatctcgggttccgcgaataaacgagatag\n" +
                    "attcattatatatggccctaagcaaaaacctcctcgtattctgttggtaattagaatcac\n" +
                    "acaatacgggttgagatattaattatttgtagtacgaagagatataaaaagatgaacaat\n" +
                    "tactcaagtcaagatgtatacgggatttataataaaaatcgggtagagatctgctttgca\n" +
                    "attcagacgtgccactaaatcgtaatatgtcgcgttacatcagaaagggtaactattatt\n" +
                    "aattaataaagggcttaatcactacatattagatcttatccgatagtcttatctattcgt\n" +
                    "tgtatttttaagcggttctaattcagtcattatatcagtgctccgagttctttattattg\n" +
                    "ttttaaggatgacaaaatgcctcttgttataacgctgggagaagcagactaagagtcgga\n" +
                    "gcagttggtagaatgaggctgcaaaagacggtctcgacgaatggacagactttactaaac\n" +
                    "caatgaaagacagaagtagagcaaagtctgaagtggtatcagcttaattatgacaaccct\n" +
                    "taatacttccctttcgccgaatactggcgtggaaaggttttaaaagtcgaagtagttaga\n" +
                    "ggcatctctcgctcataaataggtagactactcgcaatccaatgtgactatgtaatactg\n" +
                    "ggaacatcagtccgcgatgcagcgtgtttatcaaccgtccccactcgcctggggagacat\n" +
                    "gagaccacccccgtggggattattagtccgcagtaatcgactcttgacaatccttttcga\n" +
                    "ttatgtcatagcaatttacgacagttcagcgaagtgactactcggcgaaatggtattact\n" +
                    "aaagcattcgaacccacatgaatgtgattcttggcaatttctaatccactaaagcttttc\n" +
                    "cgttgaatctggttgtagatatttatataagttcactaattaagatcacggtagtatatt\n" +
                    "gatagtgatgtctttgcaagaggttggccgaggaatttacggattctctattgatacaat\n" +
                    "ttgtctggcttataactcttaaggctgaaccaggcgtttttagacgacttgatcagctgt\n" +
                    "tagaatggtttggactccctctttcatgtcagtaacatttcagccgttattgttacgata\n" +
                    "tgcttgaacaatattgatctaccacacacccatagtatattttataggtcatgctgttac\n" +
                    "ctacgagcatggtattccacttcccattcaatgagtattcaacatcactagcctcagaga\n" +
                    "tgatgacccacctctaataacgtcacgttgcggccatgtgaaacctgaacttgagtagac\n" +
                    "gatatcaagcgctttaaattgcatataacatttgagggtaaagctaagcggatgctttat\n" +
                    "ataatcaatactcaataataagatttgattgcattttagagttatgacacgacatagttc\n" +
                    "actaacgagttactattcccagatctagactgaagtactgatcgagacgatccttacgtc\n" +
                    "gatgatcgttagttatcgacttaggtcgggtctctagcggtattggtacttaaccggaca\n" +
                    "ctatactaataacccatgatcaaagcataacagaatacagacgataatttcgccaacata\n" +
                    "tatgtacagaccccaagcatgagaagctcattgaaagctatcattgaagtcccgctcaca\n" +
                    "atgtgtcttttccagacggtttaactggttcccgggagtcctggagtttcgacttacata\n" +
                    "aatggaaacaatgtattttgctaatttatctatagcgtcatttggaccaatacagaatat\n" +
                    "tatgttgcctagtaatccactataacccgcaagtgctgatagaaaatttttagacgattt\n" +
                    "ataaatgccccaagtatccctcccgtgaatcctccgttatactaattagtattcgttcat\n" +
                    "acgtataccgcgcatatatgaacatttggcgataaggcgcgtgaattgttacgtgacaga\n" +
                    "gatagcagtttcttgtgatatggttaacagacgtacatgaagggaaactttatatctata\n" +
                    "gtgatgcttccgtagaaataccgccactggtctgccaatgatgaagtatgtagctttagg\n" +
                    "tttgtactatgaggctttcgtttgtttgcagagtataacagttgcgagtgaaaaaccgac\n" +
                    "gaatttatactaatacgctttcactattggctacaaaatagggaagagtttcaatcatga\n" +
                    "gagggagtatatggatgctttgtagctaaaggtagaacgtatgtatatgctgccgttcat\n" +
                    "tcttgaaagatacataagcgataagttacgacaattataagcaacatccctaccttcgta\n" +
                    "acgatttcactgttactgcgcttgaaatacactatggggctattggcggagagaagcaga\n" +
                    "tcgcgccgagcatatacgagacctataatgttgatgatagagaaggcgtctgaattgata\n" +
                    "catcgaagtacactttctttcgtagtatctctcgtcctctttctatctccggacacaaga\n" +
                    "attaagttatatatatagagtcttaccaatcatgttgaatcctgattctcagagttcttt\n" +
                    "ggcgggccttgtgatgactgagaaacaatgcaatattgctccaaatttcctaagcaaatt\n" +
                    "ctcggttatgttatgttatcagcaaagcgttacgttatgttatttaaatctggaatgacg\n" +
                    "gagcgaagttcttatgtcggtgtgggaataattcttttgaagacagcactccttaaataa\n" +
                    "tatcgctccgtgtttgtatttatcgaatgggtctgtaaccttgcacaagcaaatcggtgg\n" +
                    "tgtatatatcggataacaattaatacgatgttcatagtgacagtatactgatcgagtcct\n" +
                    "ctaaagtcaattacctcacttaacaatctcattgatgttgtgtcattcccggtatcgccc\n" +
                    "gtagtatgtgctctgattgaccgagtgtgaaccaaggaacatctactaatgcctttgtta\n" +
                    "ggtaagatctctctgaattccttcgtgccaacttaaaacattatcaaaatttcttctact\n" +
                    "tggattaactacttttacgagcatggcaaattcccctgtggaagacggttcattattatc\n" +
                    "ggaaaccttatagaaattgcgtgttgactgaaattagatttttattgtaagagttgcatc\n" +
                    "tttgcgattcctctggtctagcttccaatgaacagtcctcccttctattcgacatcgggt\n" +
                    "ccttcgtacatgtctttgcgatgtaataattaggttcggagtgtggccttaatgggtgca\n" +
                    "actaggaatacaacgcaaatttgctgacatgatagcaaatcggtatgccggcaccaaaac\n" +
                    "gtgctccttgcttagcttgtgaatgagactcagtagttaaataaatccatatctgcaatc\n" +
                    "gattccacaggtattgtccactatctttgaactactctaagagatacaagcttagctgag\n" +
                    "accgaggtgtatatgactacgctgatatctgtaaggtaccaatgcaggcaaagtatgcga\n" +
                    "gaagctaataccggctgtttccagctttataagattaaaatttggctgtcctggcggcct\n" +
                    "cagaattgttctatcgtaatcagttggttcattaattagctaagtacgaggtacaactta\n" +
                    "tctgtcccagaacagctccacaagtttttttacagccgaaacccctgtgtgaatcttaat\n" +
                    "atccaagcgcgttatctgattagagtttacaactcagtattttatcagtacgttttgttt\n" +
                    "ccaacattacccggtatgacaaaatgacgccacgtgtcgaataatggtctgaccaatgta\n" +
                    "ggaagtgaaaagataaatat\n";

    public static final String FASTAREDUX = ">ONE Homo sapiens alu\n" +
                    "GGCCGGGCGCGGTGGCTCACGCCTGTAATCCCAGCACTTTGGGAGGCCGAGGCGGGCGGA\n" +
                    "TCACCTGAGGTCAGGAGTTCGAGACCAGCCTGGCCAACATGGTGAAACCCCGTCTCTACT\n" +
                    "AAAAATACAAAAATTAGCCGGGCGTGGTGGCGCGCGCCTGTAATCCCAGCTACTCGGGAG\n" +
                    "GCTGAGGCAGGAGAATCGCTTGAACCCGGGAGGCGGAGGTTGCAGTGAGCCGAGATCGCG\n" +
                    "CCACTGCACTCCAGCCTGGGCGACAGAGCGAGACTCCGTCTCAAAAAGGCCGGGCGCGGT\n" +
                    "GGCTCACGCCTGTAATCCCAGCACTTTGGGAGGCCGAGGCGGGCGGATCACCTGAGGTCA\n" +
                    "GGAGTTCGAGACCAGCCTGGCCAACATGGTGAAACCCCGTCTCTACTAAAAATACAAAAA\n" +
                    "TTAGCCGGGCGTGGTGGCGCGCGCCTGTAATCCCAGCTACTCGGGAGGCTGAGGCAGGAG\n" +
                    "AATCGCTTGAACCCGGGAGGCGGAGGTTGCAGTGAGCCGAGATCGCGCCACTGCACTCCA\n" +
                    "GCCTGGGCGACAGAGCGAGACTCCGTCTCAAAAAGGCCGGGCGCGGTGGCTCACGCCTGT\n" +
                    "AATCCCAGCACTTTGGGAGGCCGAGGCGGGCGGATCACCTGAGGTCAGGAGTTCGAGACC\n" +
                    "AGCCTGGCCAACATGGTGAAACCCCGTCTCTACTAAAAATACAAAAATTAGCCGGGCGTG\n" +
                    "GTGGCGCGCGCCTGTAATCCCAGCTACTCGGGAGGCTGAGGCAGGAGAATCGCTTGAACC\n" +
                    "CGGGAGGCGGAGGTTGCAGTGAGCCGAGATCGCGCCACTGCACTCCAGCCTGGGCGACAG\n" +
                    "AGCGAGACTCCGTCTCAAAAAGGCCGGGCGCGGTGGCTCACGCCTGTAATCCCAGCACTT\n" +
                    "TGGGAGGCCGAGGCGGGCGGATCACCTGAGGTCAGGAGTTCGAGACCAGCCTGGCCAACA\n" +
                    "TGGTGAAACCCCGTCTCTACTAAAAATACAAAAATTAGCCGGGCGTGGTGGCGCGCGCCT\n" +
                    "GTAATCCCAGCTACTCGGGAGGCTGAGGCAGGAGAATCGCTTGAACCCGGGAGGCGGAGG\n" +
                    "TTGCAGTGAGCCGAGATCGCGCCACTGCACTCCAGCCTGGGCGACAGAGCGAGACTCCGT\n" +
                    "CTCAAAAAGGCCGGGCGCGGTGGCTCACGCCTGTAATCCCAGCACTTTGGGAGGCCGAGG\n" +
                    "CGGGCGGATCACCTGAGGTCAGGAGTTCGAGACCAGCCTGGCCAACATGGTGAAACCCCG\n" +
                    "TCTCTACTAAAAATACAAAAATTAGCCGGGCGTGGTGGCGCGCGCCTGTAATCCCAGCTA\n" +
                    "CTCGGGAGGCTGAGGCAGGAGAATCGCTTGAACCCGGGAGGCGGAGGTTGCAGTGAGCCG\n" +
                    "AGATCGCGCCACTGCACTCCAGCCTGGGCGACAGAGCGAGACTCCGTCTCAAAAAGGCCG\n" +
                    "GGCGCGGTGGCTCACGCCTGTAATCCCAGCACTTTGGGAGGCCGAGGCGGGCGGATCACC\n" +
                    "TGAGGTCAGGAGTTCGAGACCAGCCTGGCCAACATGGTGAAACCCCGTCTCTACTAAAAA\n" +
                    "TACAAAAATTAGCCGGGCGTGGTGGCGCGCGCCTGTAATCCCAGCTACTCGGGAGGCTGA\n" +
                    "GGCAGGAGAATCGCTTGAACCCGGGAGGCGGAGGTTGCAGTGAGCCGAGATCGCGCCACT\n" +
                    "GCACTCCAGCCTGGGCGACAGAGCGAGACTCCGTCTCAAAAAGGCCGGGCGCGGTGGCTC\n" +
                    "ACGCCTGTAATCCCAGCACTTTGGGAGGCCGAGGCGGGCGGATCACCTGAGGTCAGGAGT\n" +
                    "TCGAGACCAGCCTGGCCAACATGGTGAAACCCCGTCTCTACTAAAAATACAAAAATTAGC\n" +
                    "CGGGCGTGGTGGCGCGCGCCTGTAATCCCAGCTACTCGGGAGGCTGAGGCAGGAGAATCG\n" +
                    "CTTGAACCCGGGAGGCGGAGGTTGCAGTGAGCCGAGATCGCGCCACTGCACTCCAGCCTG\n" +
                    "GGCGACAGAGCGAGACTCCG\n" +
                    ">TWO IUB ambiguity codes\n" +
                    "cttBtatcatatgctaKggNcataaaSatgtaaaDcDRtBggDtctttataattcBgtcg\n" +
                    "tactDtDagcctatttSVHtHttKtgtHMaSattgWaHKHttttagacatWatgtRgaaa\n" +
                    "NtactMcSMtYtcMgRtacttctWBacgaaatatagScDtttgaagacacatagtVgYgt\n" +
                    "cattHWtMMWcStgttaggKtSgaYaaccWStcgBttgcgaMttBYatcWtgacaYcaga\n" +
                    "gtaBDtRacttttcWatMttDBcatWtatcttactaBgaYtcttgttttttttYaaScYa\n" +
                    "HgtgttNtSatcMtcVaaaStccRcctDaataataStcYtRDSaMtDttgttSagtRRca\n" +
                    "tttHatSttMtWgtcgtatSSagactYaaattcaMtWatttaSgYttaRgKaRtccactt\n" +
                    "tattRggaMcDaWaWagttttgacatgttctacaaaRaatataataaMttcgDacgaSSt\n" +
                    "acaStYRctVaNMtMgtaggcKatcttttattaaaaagVWaHKYagtttttatttaacct\n" +
                    "tacgtVtcVaattVMBcttaMtttaStgacttagattWWacVtgWYagWVRctDattBYt\n" +
                    "gtttaagaagattattgacVatMaacattVctgtBSgaVtgWWggaKHaatKWcBScSWa\n" +
                    "accRVacacaaactaccScattRatatKVtactatatttHttaagtttSKtRtacaaagt\n" +
                    "RDttcaaaaWgcacatWaDgtDKacgaacaattacaRNWaatHtttStgttattaaMtgt\n" +
                    "tgDcgtMgcatBtgcttcgcgaDWgagctgcgaggggVtaaScNatttacttaatgacag\n" +
                    "cccccacatYScaMgtaggtYaNgttctgaMaacNaMRaacaaacaKctacatagYWctg\n" +
                    "ttWaaataaaataRattagHacacaagcgKatacBttRttaagtatttccgatctHSaat\n" +
                    "actcNttMaagtattMtgRtgaMgcataatHcMtaBSaRattagttgatHtMttaaKagg\n" +
                    "YtaaBataSaVatactWtataVWgKgttaaaacagtgcgRatatacatVtHRtVYataSa\n" +
                    "KtWaStVcNKHKttactatccctcatgWHatWaRcttactaggatctataDtDHBttata\n" +
                    "aaaHgtacVtagaYttYaKcctattcttcttaataNDaaggaaaDYgcggctaaWSctBa\n" +
                    "aNtgctggMBaKctaMVKagBaactaWaDaMaccYVtNtaHtVWtKgRtcaaNtYaNacg\n" +
                    "gtttNattgVtttctgtBaWgtaattcaagtcaVWtactNggattctttaYtaaagccgc\n" +
                    "tcttagHVggaYtgtNcDaVagctctctKgacgtatagYcctRYHDtgBattDaaDgccK\n" +
                    "tcHaaStttMcctagtattgcRgWBaVatHaaaataYtgtttagMDMRtaataaggatMt\n" +
                    "ttctWgtNtgtgaaaaMaatatRtttMtDgHHtgtcattttcWattRSHcVagaagtacg\n" +
                    "ggtaKVattKYagactNaatgtttgKMMgYNtcccgSKttctaStatatNVataYHgtNa\n" +
                    "BKRgNacaactgatttcctttaNcgatttctctataScaHtataRagtcRVttacDSDtt\n" +
                    "aRtSatacHgtSKacYagttMHtWataggatgactNtatSaNctataVtttRNKtgRacc\n" +
                    "tttYtatgttactttttcctttaaacatacaHactMacacggtWataMtBVacRaSaatc\n" +
                    "cgtaBVttccagccBcttaRKtgtgcctttttRtgtcagcRttKtaaacKtaaatctcac\n" +
                    "aattgcaNtSBaaccgggttattaaBcKatDagttactcttcattVtttHaaggctKKga\n" +
                    "tacatcBggScagtVcacattttgaHaDSgHatRMaHWggtatatRgccDttcgtatcga\n" +
                    "aacaHtaagttaRatgaVacttagattVKtaaYttaaatcaNatccRttRRaMScNaaaD\n" +
                    "gttVHWgtcHaaHgacVaWtgttScactaagSgttatcttagggDtaccagWattWtRtg\n" +
                    "ttHWHacgattBtgVcaYatcggttgagKcWtKKcaVtgaYgWctgYggVctgtHgaNcV\n" +
                    "taBtWaaYatcDRaaRtSctgaHaYRttagatMatgcatttNattaDttaattgttctaa\n" +
                    "ccctcccctagaWBtttHtBccttagaVaatMcBHagaVcWcagBVttcBtaYMccagat\n" +
                    "gaaaaHctctaacgttagNWRtcggattNatcRaNHttcagtKttttgWatWttcSaNgg\n" +
                    "gaWtactKKMaacatKatacNattgctWtatctaVgagctatgtRaHtYcWcttagccaa\n" +
                    "tYttWttaWSSttaHcaaaaagVacVgtaVaRMgattaVcDactttcHHggHRtgNcctt\n" +
                    "tYatcatKgctcctctatVcaaaaKaaaagtatatctgMtWtaaaacaStttMtcgactt\n" +
                    "taSatcgDataaactaaacaagtaaVctaggaSccaatMVtaaSKNVattttgHccatca\n" +
                    "cBVctgcaVatVttRtactgtVcaattHgtaaattaaattttYtatattaaRSgYtgBag\n" +
                    "aHSBDgtagcacRHtYcBgtcacttacactaYcgctWtattgSHtSatcataaatataHt\n" +
                    "cgtYaaMNgBaatttaRgaMaatatttBtttaaaHHKaatctgatWatYaacttMctctt\n" +
                    "ttVctagctDaaagtaVaKaKRtaacBgtatccaaccactHHaagaagaaggaNaaatBW\n" +
                    "attccgStaMSaMatBttgcatgRSacgttVVtaaDMtcSgVatWcaSatcttttVatag\n" +
                    "ttactttacgatcaccNtaDVgSRcgVcgtgaacgaNtaNatatagtHtMgtHcMtagaa\n" +
                    "attBgtataRaaaacaYKgtRccYtatgaagtaataKgtaaMttgaaRVatgcagaKStc\n" +
                    "tHNaaatctBBtcttaYaBWHgtVtgacagcaRcataWctcaBcYacYgatDgtDHccta\n" +
                    ">THREE Homo sapiens frequency\n" +
                    "aacacttcaccaggtatcgtgaaggctcaagattacccagagaacctttgcaatataaga\n" +
                    "atatgtatgcagcattaccctaagtaattatattctttttctgactcaaagtgacaagcc\n" +
                    "ctagtgtatattaaatcggtatatttgggaaattcctcaaactatcctaatcaggtagcc\n" +
                    "atgaaagtgatcaaaaaagttcgtacttataccatacatgaattctggccaagtaaaaaa\n" +
                    "tagattgcgcaaaattcgtaccttaagtctctcgccaagatattaggatcctattactca\n" +
                    "tatcgtgtttttctttattgccgccatccccggagtatctcacccatccttctcttaaag\n" +
                    "gcctaatattacctatgcaaataaacatatattgttgaaaattgagaacctgatcgtgat\n" +
                    "tcttatgtgtaccatatgtatagtaatcacgcgactatatagtgctttagtatcgcccgt\n" +
                    "gggtgagtgaatattctgggctagcgtgagatagtttcttgtcctaatatttttcagatc\n" +
                    "gaatagcttctatttttgtgtttattgacatatgtcgaaactccttactcagtgaaagtc\n" +
                    "atgaccagatccacgaacaatcttcggaatcagtctcgttttacggcggaatcttgagtc\n" +
                    "taacttatatcccgtcgcttactttctaacaccccttatgtatttttaaaattacgttta\n" +
                    "ttcgaacgtacttggcggaagcgttattttttgaagtaagttacattgggcagactcttg\n" +
                    "acattttcgatacgactttctttcatccatcacaggactcgttcgtattgatatcagaag\n" +
                    "ctcgtgatgattagttgtcttctttaccaatactttgaggcctattctgcgaaatttttg\n" +
                    "ttgccctgcgaacttcacataccaaggaacacctcgcaacatgccttcatatccatcgtt\n" +
                    "cattgtaattcttacacaatgaatcctaagtaattacatccctgcgtaaaagatggtagg\n" +
                    "ggcactgaggatatattaccaagcatttagttatgagtaatcagcaatgtttcttgtatt\n" +
                    "aagttctctaaaatagttacatcgtaatgttatctcgggttccgcgaataaacgagatag\n" +
                    "attcattatatatggccctaagcaaaaacctcctcgtattctgttggtaattagaatcac\n" +
                    "acaatacgggttgagatattaattatttgtagtacgaagagatataaaaagatgaacaat\n" +
                    "tactcaagtcaagatgtatacgggatttataataaaaatcgggtagagatctgctttgca\n" +
                    "attcagacgtgccactaaatcgtaatatgtcgcgttacatcagaaagggtaactattatt\n" +
                    "aattaataaagggcttaatcactacatattagatcttatccgatagtcttatctattcgt\n" +
                    "tgtatttttaagcggttctaattcagtcattatatcagtgctccgagttctttattattg\n" +
                    "ttttaaggatgacaaaatgcctcttgttataacgctgggagaagcagactaagagtcgga\n" +
                    "gcagttggtagaatgaggctgcaaaagacggtctcgacgaatggacagactttactaaac\n" +
                    "caatgaaagacagaagtagagcaaagtctgaagtggtatcagcttaattatgacaaccct\n" +
                    "taatacttccctttcgccgaatactggcgtggaaaggttttaaaagtcgaagtagttaga\n" +
                    "ggcatctctcgctcataaataggtagactactcgcaatccaatgtgactatgtaatactg\n" +
                    "ggaacatcagtccgcgatgcagcgtgtttatcaaccgtccccactcgcctggggagacat\n" +
                    "gagaccacccccgtggggattattagtccgcagtaatcgactcttgacaatccttttcga\n" +
                    "ttatgtcatagcaatttacgacagttcagcgaagtgactactcggcgaaatggtattact\n" +
                    "aaagcattcgaacccacatgaatgtgattcttggcaatttctaatccactaaagcttttc\n" +
                    "cgttgaatctggttgtagatatttatataagttcactaattaagatcacggtagtatatt\n" +
                    "gatagtgatgtctttgcaagaggttggccgaggaatttacggattctctattgatacaat\n" +
                    "ttgtctggcttataactcttaaggctgaaccaggcgtttttagacgacttgatcagctgt\n" +
                    "tagaatggtttggactccctctttcatgtcagtaacatttcagccgttattgttacgata\n" +
                    "tgcttgaacaatattgatctaccacacacccatagtatattttataggtcatgctgttac\n" +
                    "ctacgagcatggtattccacttcccattcaatgagtattcaacatcactagcctcagaga\n" +
                    "tgatgacccacctctaataacgtcacgttgcggccatgtgaaacctgaacttgagtagac\n" +
                    "gatatcaagcgctttaaattgcatataacatttgagggtaaagctaagcggatgctttat\n" +
                    "ataatcaatactcaataataagatttgattgcattttagagttatgacacgacatagttc\n" +
                    "actaacgagttactattcccagatctagactgaagtactgatcgagacgatccttacgtc\n" +
                    "gatgatcgttagttatcgacttaggtcgggtctctagcggtattggtacttaaccggaca\n" +
                    "ctatactaataacccatgatcaaagcataacagaatacagacgataatttcgccaacata\n" +
                    "tatgtacagaccccaagcatgagaagctcattgaaagctatcattgaagtcccgctcaca\n" +
                    "atgtgtcttttccagacggtttaactggttcccgggagtcctggagtttcgacttacata\n" +
                    "aatggaaacaatgtattttgctaatttatctatagcgtcatttggaccaatacagaatat\n" +
                    "tatgttgcctagtaatccactataacccgcaagtgctgatagaaaatttttagacgattt\n" +
                    "ataaatgccccaagtatccctcccgtgaatcctccgttatactaattagtattcgttcat\n" +
                    "acgtataccgcgcatatatgaacatttggcgataaggcgcgtgaattgttacgtgacaga\n" +
                    "gatagcagtttcttgtgatatggttaacagacgtacatgaagggaaactttatatctata\n" +
                    "gtgatgcttccgtagaaataccgccactggtctgccaatgatgaagtatgtagctttagg\n" +
                    "tttgtactatgaggctttcgtttgtttgcagagtataacagttgcgagtgaaaaaccgac\n" +
                    "gaatttatactaatacgctttcactattggctacaaaatagggaagagtttcaatcatga\n" +
                    "gagggagtatatggatgctttgtagctaaaggtagaacgtatgtatatgctgccgttcat\n" +
                    "tcttgaaagatacataagcgataagttacgacaattataagcaacatccctaccttcgta\n" +
                    "acgatttcactgttactgcgcttgaaatacactatggggctattggcggagagaagcaga\n" +
                    "tcgcgccgagcatatacgagacctataatgttgatgatagagaaggcgtctgaattgata\n" +
                    "catcgaagtacactttctttcgtagtatctctcgtcctctttctatctccggacacaaga\n" +
                    "attaagttatatatatagagtcttaccaatcatgttgaatcctgattctcagagttcttt\n" +
                    "ggcgggccttgtgatgactgagaaacaatgcaatattgctccaaatttcctaagcaaatt\n" +
                    "ctcggttatgttatgttatcagcaaagcgttacgttatgttatttaaatctggaatgacg\n" +
                    "gagcgaagttcttatgtcggtgtgggaataattcttttgaagacagcactccttaaataa\n" +
                    "tatcgctccgtgtttgtatttatcgaatgggtctgtaaccttgcacaagcaaatcggtgg\n" +
                    "tgtatatatcggataacaattaatacgatgttcatagtgacagtatactgatcgagtcct\n" +
                    "ctaaagtcaattacctcacttaacaatctcattgatgttgtgtcattcccggtatcgccc\n" +
                    "gtagtatgtgctctgattgaccgagtgtgaaccaaggaacatctactaatgcctttgtta\n" +
                    "ggtaagatctctctgaattccttcgtgccaacttaaaacattatcaaaatttcttctact\n" +
                    "tggattaactacttttacgagcatggcaaattcccctgtggaagacggttcattattatc\n" +
                    "ggaaaccttatagaaattgcgtgttgactgaaattagatttttattgtaagagttgcatc\n" +
                    "tttgcgattcctctggtctagcttccaatgaacagtcctcccttctattcgacatcgggt\n" +
                    "ccttcgtacatgtctttgcgatgtaataattaggttcggagtgtggccttaatgggtgca\n" +
                    "actaggaatacaacgcaaatttgctgacatgatagcaaatcggtatgccggcaccaaaac\n" +
                    "gtgctccttgcttagcttgtgaatgagactcagtagttaaataaatccatatctgcaatc\n" +
                    "gattccacaggtattgtccactatctttgaactactctaagagatacaagcttagctgag\n" +
                    "accgaggtgtatatgactacgctgatatctgtaaggtaccaatgcaggcaaagtatgcga\n" +
                    "gaagctaataccggctgtttccagctttataagattaaaatttggctgtcctggcggcct\n" +
                    "cagaattgttctatcgtaatcagttggttcattaattagctaagtacgaggtacaactta\n" +
                    "tctgtcccagaacagctccacaagtttttttacagccgaaacccctgtgtgaatcttaat\n" +
                    "atccaagcgcgttatctgattagagtttacaactcagtattttatcagtacgttttgttt\n" +
                    "ccaacattacccggtatgacaaaatgacgccacgtgtcgaataatggtctgaccaatgta\n" +
                    "ggaagtgaaaagataaatat\n";

    public static final String MANDELBROT = "UDQKMjAwIDIwMAoAAAAAAAAAAAAAAAAAAAAAAAACAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                    "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                    "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                    "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                    "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA8AAAAAAAAAAAAAAAAAAAAAAAAAA" +
                    "AAAAAAHoAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAF8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAfAAAAAAAAAAAAAAAAA" +
                    "AAAAAAAAAAAAAAAHwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAv4AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAP/4AAAAAAA" +
                    "AAAAAAAAAAAAAAAAAAAAAABf/+AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAP//0AAAAAAAAAAAAAAAAAAAAAAAAAAAAAD/" +
                    "//wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAf//4AAAAAAAAAAAAAAAAAAAAAAAAAAAAAf//8AAAAAAAAAAAAAAAAAAAAAA" +
                    "AAAAAAAD///gAAAAAAAAAAAAAAAAAAAAAAAAAAAAF///wAAAAAAAAAAAAAAAAAAAAAAAAAAAAA///+AAAAAAAAAAAAA" +
                    "AAAAAAAAAAAAAAAAD///wAAAAAAAAAAAAAAAAAAAAAAAAAAAAC///wAAAAAAAAAAAAAAAAAAAAAAAAAAAAA///5AAAA" +
                    "AAAAAAAAAAAAAAAAAAAAAAAAAD///wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAf//gAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                    "AD//4AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAf/8AAAAAAAAAAAAAAAAAAAAAAAABAAEAAH/+AAAAAAAAAAAAAAAAAAAA" +
                    "AAAAAAAAAwAf+ARggAAAAAAAAAAAAAAAAAAAAAAACQY////WAAAAAAAAAAAAAAAAAAAAAAAAAAGjP////sAgAAAAAAA" +
                    "AAAAAAAAAAAAAAAgF6//////p4AAAAAAAAAAAAAAAAAAAAAUIB///////+fAAAAAAAAAAAAAAAAAAAAALGAP///////" +
                    "3gAQAAAAAAAAAAAAAAAAAAC/w2////////wAAAAAAAAAAAAAAAAAAAAAH8H/////////AAAAAAAAAAAAAAAAAAAAAD/" +
                    "l/////////2AYkAAAAAAAAAAAAAAAAAE/7//////////gfuAAAAAAAAAAAAAAAAAB/9//////////6H/AAAAAAAAAAA" +
                    "AAAAAAAP////////////j/gAAAAAAAAAAAAAAAAAB////////////9/4AAAAAAAAAAAAAAAAAAH////////////v+AA" +
                    "AAAAAAAAAAAAAAAAAP/////////////wAAAAAAAAAAAAAAAAAAP/////////////+AAAAAAAAAAAAAAAAAAf///////" +
                    "//////8AAAAAAAAAAAAAAAAAAz/////////////+gAAAAAAAAAAAAAAAAAP/////////////8AAAAAAAAAAAAAAAACA" +
                    "B//////////////wAAAAAAAAAAAAAAAAEcv/////////////6AAAAAAAAAAAAAAAAB/f//////////////YAAAAAAAA" +
                    "AAAAAAAAn///////////////8AAAAAAAAAAAAAAAAI////////////////QAAAAAAAAAAAAAAAAv///////////////" +
                    "3AAAAAAAAAAAAAAAAD////////////////gAAAAAAAAAAAAAAAAf///////////////oAAAAAAAAAAAAAAAAH//////" +
                    "/////////8AAAAAAAAAAAAAAABF////////////////hAAAAAAAAAAAAAAAB////////////////9AAAAAAAAAAAAAA" +
                    "AKf////////////////cAAAAAAAAAAAAAAH//////////////////gAAAAAAAAAAAAAAf/////////////////+AAAA" +
                    "AAAAAAAAAAH/////////////////+AAAAAAAAAAAAAAAf/////////////////AAAAAAAAAAAAAABX/////////////" +
                    "///+gAAAAAAAAAEAAAAD/////////////////wAAAAAAAgACBgAA+/////////////////4AAAAAAAAAB4AAAD/////" +
                    "/////////////AAAAAAAHoA8AAAAf/////////////////8AAAAAAA/GHMyAAD/////////////////+AAAAAAAfi//" +
                    "cAAA//////////////////gAAAAAAX9///AAB//////////////////4AAAAAAA////5AAP//////////////////gA" +
                    "AAAAAP////wAD//////////////////4AAAAAAC////+QD///////////////////wAAAAAA/////4AP///////////" +
                    "//////8AAAAAABf/////AH//////////////////gAAAAAAP/////4D//////////////////8AAAAADX//////g///" +
                    "///////////////8AAAAAAP//////wH//////////////////gAAAAAH///////j//////////////////4AAAAAAf/" +
                    "/////w///////////////////gAAAAAP//////+P/////////////////+gAAAAAP///////j//////////////////" +
                    "wAAAAAD///////8//////////////////8AAAAAAf///////P//////////////////AAAAAAP///////z/////////" +
                    "////////+wAAAAAD///////+//////////////////gAAAAZB////////v/////////////////8AAAAH+f////////" +
                    "/////////////////8AAAAB/j/////////////////////////+gAAAA////////////////////////////gAAAAP/" +
                    "//////////////////////////wAAAAH///////////////////////////wAAAAH//////////////////////////" +
                    "/wAAA/////////////////////////////wAAAAAf///////////////////////////AAAAAB/////////////////" +
                    "//////////8AAAAAP///////////////////////////wAAAAD///////////////////////////+AAAAAf4//////" +
                    "////////////////////oAAAAH+f/////////////////////////8AAAABkH///////+//////////////////wAAA" +
                    "AAA////////v/////////////////4AAAAAAP///////z/////////////////+wAAAAAB///////8/////////////" +
                    "/////8AAAAAA////////P//////////////////AAAAAAP///////j//////////////////wAAAAAA///////4////" +
                    "//////////////6AAAAAAH//////8P//////////////////4AAAAAH///////j//////////////////4AAAAAA///" +
                    "////Af/////////////////+AAAAAA1//////4P//////////////////AAAAAAAP/////4D//////////////////8" +
                    "AAAAAAF/////8Af/////////////////+AAAAAAAP////+AD//////////////////AAAAAAAC////+QD//////////" +
                    "/////////wAAAAAA/////AAP//////////////////gAAAAAAP///+QAD//////////////////4AAAAAAX9///AAB/" +
                    "/////////////////4AAAAAAB+L/9wAAD/////////////////+AAAAAAAPxhzMgAA//////////////////gAAAAAA" +
                    "HoA8AAAAf/////////////////8AAAAAAAAAHgAAAP/////////////////8AAAAAAAIAAgYAAPv///////////////" +
                    "/+AAAAAAAAAAEAAAAD/////////////////wAAAAAAAAAAAAAAFf////////////////6AAAAAAAAAAAAAAAH//////" +
                    "///////////wAAAAAAAAAAAAAAH/////////////////+AAAAAAAAAAAAAAB//////////////////4AAAAAAAAAAAA" +
                    "AB//////////////////4AAAAAAAAAAAAAAKf////////////////cAAAAAAAAAAAAAAAH////////////////0AAAA" +
                    "AAAAAAAAAAARf///////////////4QAAAAAAAAAAAAAAAH///////////////8AAAAAAAAAAAAAAAAB////////////" +
                    "///+gAAAAAAAAAAAAAAAA////////////////4AAAAAAAAAAAAAAAAv///////////////3AAAAAAAAAAAAAAAAj///" +
                    "////////////9AAAAAAAAAAAAAAAAJ////////////////AAAAAAAAAAAAAAAAB/f//////////////YAAAAAAAAAAA" +
                    "AAAAARy//////////////oAAAAAAAAAAAAAAAAgAf/////////////8AAAAAAAAAAAAAAAAAAP/////////////8AAA" +
                    "AAAAAAAAAAAAAAADP/////////////6AAAAAAAAAAAAAAAAAAH//////////////AAAAAAAAAAAAAAAAAAAP///////" +
                    "//////+AAAAAAAAAAAAAAAAAAA//////////////AAAAAAAAAAAAAAAAAAB////////////7/gAAAAAAAAAAAAAAAAA" +
                    "B////////////9/4AAAAAAAAAAAAAAAAAA////////////+P+AAAAAAAAAAAAAAAAAAf/f/////////+h/wAAAAAAAA" +
                    "AAAAAAAAAE/7//////////gfuAAAAAAAAAAAAAAAAAAP+X/////////YBiQAAAAAAAAAAAAAAAAAB/B/////////wAA" +
                    "AAAAAAAAAAAAAAAAAAC/w2////////wAAAAAAAAAAAAAAAAAAAAAsYA////////eABAAAAAAAAAAAAAAAAAAAFCAf//" +
                    "/////nwAAAAAAAAAAAAAAAAAAAAAAgF6//////p4AAAAAAAAAAAAAAAAAAAAAAAAaM////+wCAAAAAAAAAAAAAAAAAA" +
                    "AAAAAAkGP///1gAAAAAAAAAAAAAAAAAAAAAAAAAAAwAf+ARggAAAAAAAAAAAAAAAAAAAAAEAAQAAf/4AAAAAAAAAAAA" +
                    "AAAAAAAAAAAAAAAAAAH//AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAD//4AAAAAAAAAAAAAAAAAAAAAAAAAAAAAB//+AAA" +
                    "AAAAAAAAAAAAAAAAAAAAAAAAAAA///8AAAAAAAAAAAAAAAAAAAAAAAAAAAAA///5AAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                    "AAL///AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA///8AAAAAAAAAAAAAAAAAAAAAAAAAAAAA///+AAAAAAAAAAAAAAAAAA" +
                    "AAAAAAAAAAAX///AAAAAAAAAAAAAAAAAAAAAAAAAAAAAA///4AAAAAAAAAAAAAAAAAAAAAAAAAAAAAf//8AAAAAAAAA" +
                    "AAAAAAAAAAAAAAAAAAAAB///gAAAAAAAAAAAAAAAAAAAAAAAAAAAAA///8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAP//0" +
                    "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAF//4AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAD/+AAAAAAAAAAAAAAAAAAAAAAAAA" +
                    "AAAAAAv4AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAfAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHwAAAAAAAAAAAAAAAA" +
                    "AAAAAAAAAAAAAAAF8AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAegAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAPAAAAAAAA" +
                    "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                    "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                    "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                    "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA==";

    public static final String PIDIGITS = "3141592653\t:10\n" +
                    "5897932384\t:20\n" +
                    "6264338327\t:30\n" +
                    "9502884197\t:40\n" +
                    "1693993751\t:50\n" +
                    "0582097494\t:60\n" +
                    "4592307816\t:70\n" +
                    "4062862089\t:80\n" +
                    "9862803482\t:90\n" +
                    "5342117067\t:100\n" +
                    "9821480865\t:110\n" +
                    "1328230664\t:120\n" +
                    "7093844609\t:130\n" +
                    "5505822317\t:140\n" +
                    "2535940812\t:150\n" +
                    "8481117450\t:160\n" +
                    "2841027019\t:170\n" +
                    "3852110555\t:180\n" +
                    "9644622948\t:190\n" +
                    "9549303819\t:200\n" +
                    "6442881097\t:210\n" +
                    "5665933446\t:220\n" +
                    "1284756482\t:230\n" +
                    "3378678316\t:240\n" +
                    "5271201909\t:250\n" +
                    "1456485669\t:260\n" +
                    "2346034861\t:270\n" +
                    "0454326648\t:280\n" +
                    "2133936072\t:290\n" +
                    "6024914127\t:300\n" +
                    "3724587006\t:310\n" +
                    "6063155881\t:320\n" +
                    "7488152092\t:330\n" +
                    "0962829254\t:340\n" +
                    "0917153643\t:350\n" +
                    "6789259036\t:360\n" +
                    "0011330530\t:370\n" +
                    "5488204665\t:380\n" +
                    "2138414695\t:390\n" +
                    "1941511609\t:400\n" +
                    "4330572703\t:410\n" +
                    "6575959195\t:420\n" +
                    "3092186117\t:430\n" +
                    "3819326117\t:440\n" +
                    "9310511854\t:450\n" +
                    "8074462379\t:460\n" +
                    "9627495673\t:470\n" +
                    "5188575272\t:480\n" +
                    "4891227938\t:490\n" +
                    "1830119491\t:500\n" +
                    "2983367336\t:510\n" +
                    "2440656643\t:520\n" +
                    "0860213949\t:530\n" +
                    "4639522473\t:540\n" +
                    "7190702179\t:550\n" +
                    "8609437027\t:560\n" +
                    "7053921717\t:570\n" +
                    "6293176752\t:580\n" +
                    "3846748184\t:590\n" +
                    "6766940513\t:600\n" +
                    "2000568127\t:610\n" +
                    "1452635608\t:620\n" +
                    "2778577134\t:630\n" +
                    "2757789609\t:640\n" +
                    "1736371787\t:650\n" +
                    "2146844090\t:660\n" +
                    "1224953430\t:670\n" +
                    "1465495853\t:680\n" +
                    "7105079227\t:690\n" +
                    "9689258923\t:700\n" +
                    "5420199561\t:710\n" +
                    "1212902196\t:720\n" +
                    "0864034418\t:730\n" +
                    "1598136297\t:740\n" +
                    "7477130996\t:750\n" +
                    "0518707211\t:760\n" +
                    "3499999983\t:770\n" +
                    "7297804995\t:780\n" +
                    "1059731732\t:790\n" +
                    "8160963185\t:800\n" +
                    "9502445945\t:810\n" +
                    "5346908302\t:820\n" +
                    "6425223082\t:830\n" +
                    "5334468503\t:840\n" +
                    "5261931188\t:850\n";

    @Test
    public void fasta_cint() throws Exception {
        TestRunner.run("fasta.cint", new String[]{"1000"}, "", FASTA, "", 0);
    }

    @Test
    public void fasta_gcc() throws Exception {
        TestRunner.run("fasta.gcc", new String[0], "", FASTA, "", 0);
    }

    @Test
    public void fasta_gcc4() throws Exception {
        TestRunner.run("fasta.gcc-4", new String[0], "", FASTA, "", 0);
    }

    @Test
    public void fastaredux_gcc2() throws Exception {
        TestRunner.run("fastaredux.gcc-2", new String[]{"1000"}, "", FASTAREDUX, "", 0);
    }

    @Test
    public void fastaredux_gcc3() throws Exception {
        TestRunner.run("fastaredux.gcc-3", new String[]{"1000"}, "", FASTAREDUX, "", 0);
    }

    @Test
    public void fastaredux_gcc5() throws Exception {
        TestRunner.run("fastaredux.gcc-5", new String[]{"1000"}, "", FASTAREDUX, "", 0);
    }

    @Test
    public void fastaredux_gpp() throws Exception {
        TestRunner.run("fastaredux.gpp", new String[]{"1000"}, "", FASTAREDUX, "", 0);
    }

    @Test
    public void fastaredux_gpp2() throws Exception {
        TestRunner.run("fastaredux.gpp-2", new String[]{"1000"}, "", FASTAREDUX, "", 0);
    }

    @Test
    public void fastaredux_gpp5() throws Exception {
        TestRunner.run("fastaredux.gpp-5", new String[]{"1000"}, "", FASTAREDUX, "", 0);
    }

    @Test
    public void fannkuchredux_cint() throws Exception {
        TestRunner.run("fannkuchredux.cint", new String[]{"7"}, "", "228\nPfannkuchen(7) = 16\n", "", 0);
    }

    @Test
    public void fannkuchredux_gcc() throws Exception {
        TestRunner.run("fannkuchredux.gcc", new String[]{"7"}, "", "228\nPfannkuchen(7) = 16\n", "", 0);
    }

    @Test
    public void fannkuchredux_gcc3() throws Exception {
        TestRunner.run("fannkuchredux.gcc-3", new String[]{"7"}, "", "228\nPfannkuchen(7) = 16\n", "", 0);
    }

    @Test
    public void mandelbrot_cint() throws Exception {
        TestRunner.runBinary("mandelbrot.cint", new String[]{"200"}, "", MANDELBROT, "", 0);
    }

    @Test
    public void mandelbrot_gcc2() throws Exception {
        TestRunner.runBinary("mandelbrot.gcc-2", new String[]{"200"}, "", MANDELBROT, "", 0);
    }

    @Test
    public void mandelbrot_gcc4() throws Exception {
        TestRunner.runBinary("mandelbrot.gcc-4", new String[]{"200"}, "", MANDELBROT, "", 0);
    }

    @Test
    public void mandelbrot_gcc8() throws Exception {
        TestRunner.runBinary("mandelbrot.gcc-8", new String[]{"200"}, "", MANDELBROT, "", 0);
    }

    @Test
    public void mandelbrot_gcc9() throws Exception {
        TestRunner.runBinary("mandelbrot.gcc-9", new String[]{"200"}, "", MANDELBROT, "", 0);
    }

    @Ignore("interpreter bug")
    @Test
    public void mandelbrot_gpp() throws Exception {
        TestRunner.runBinary("mandelbrot.gpp", new String[]{"200"}, "", MANDELBROT, "", 0);
    }

    @Test
    public void mandelbrot_gpp2() throws Exception {
        TestRunner.runBinary("mandelbrot.gpp-2", new String[]{"200"}, "", MANDELBROT, "", 0);
    }

    @Test
    public void mandelbrot_gpp3() throws Exception {
        TestRunner.runBinary("mandelbrot.gpp-3", new String[]{"200"}, "", MANDELBROT, "", 0);
    }

    @Test
    public void mandelbrot_gpp5() throws Exception {
        TestRunner.runBinary("mandelbrot.gpp-5", new String[]{"200"}, "", MANDELBROT, "", 0);
    }

    @Test
    public void mandelbrot_gpp6() throws Exception {
        TestRunner.runBinary("mandelbrot.gpp-6", new String[]{"200"}, "", MANDELBROT, "", 0);
    }

    @Test
    public void mandelbrot_gpp7() throws Exception {
        TestRunner.runBinary("mandelbrot.gpp-7", new String[]{"200"}, "", MANDELBROT, "", 0);
    }

    @Test
    public void mandelbrot_gpp8() throws Exception {
        TestRunner.runBinary("mandelbrot.gpp-8", new String[]{"200"}, "", MANDELBROT, "", 0);
    }

    @Test
    public void mandelbrot_gpp9() throws Exception {
        TestRunner.runBinary("mandelbrot.gpp-9", new String[]{"200"}, "", MANDELBROT, "", 0);
    }

    @Test
    public void binarytrees_gcc() throws Exception {
        String stdout = "stretch tree of depth 11\t check: -1\n" +
                        "2048\t trees of depth 4\t check: -2048\n" +
                        "512\t trees of depth 6\t check: -512\n" +
                        "128\t trees of depth 8\t check: -128\n" +
                        "32\t trees of depth 10\t check: -32\n" +
                        "long lived tree of depth 10\t check: -1\n";
        TestRunner.run("binarytrees.gcc", new String[]{"10"}, "", stdout, "", 0);
    }

    @Test
    public void binarytrees_gcc2() throws Exception {
        String stdout = "stretch tree of depth 11\t check: -1\n" +
                        "2048\t trees of depth 4\t check: -2048\n" +
                        "512\t trees of depth 6\t check: -512\n" +
                        "128\t trees of depth 8\t check: -128\n" +
                        "32\t trees of depth 10\t check: -32\n" +
                        "long lived tree of depth 10\t check: -1\n";
        TestRunner.run("binarytrees.gcc-2", new String[]{"10"}, "", stdout, "", 0);
    }

    @Test
    public void spectralnorm_cint() throws Exception {
        TestRunner.run("spectralnorm.cint", new String[]{"100"}, "", "1.274219991\n", "", 0);
    }

    @Test
    public void spectralnorm_gcc() throws Exception {
        TestRunner.run("spectralnorm.gcc", new String[]{"100"}, "", "1.274219991\n", "", 0);
    }

    @Ignore("missing opcode")
    @Test
    public void spectralnorm_gcc2() throws Exception {
        TestRunner.run("spectralnorm.gcc-2", new String[]{"100"}, "", "1.274219991\n", "", 0);
    }

    @Test
    public void spectralnorm_gcc3() throws Exception {
        TestRunner.run("spectralnorm.gcc-3", new String[]{"100"}, "", "1.274219991\n", "", 0);
    }

    @Test
    public void spectralnorm_gcc5() throws Exception {
        TestRunner.run("spectralnorm.gcc-5", new String[]{"100"}, "", "1.274219991\n", "", 0);
    }

    @Ignore("dynamically linked")
    @Test
    public void pidigits_cint4() throws Exception {
        assumeTrue(HostTest.isX86);
        TestRunner.run("pidigits.cint-4", new String[]{"850"}, "", PIDIGITS, "", 0);
    }

    @Test
    public void pidigits_gcc() throws Exception {
        assumeTrue(HostTest.isX86);
        TestRunner.run("pidigits.gcc", new String[]{"850"}, "", PIDIGITS, "", 0);
    }

    @Ignore("dynamically linked")
    @Test
    public void pidigits_gcc4() throws Exception {
        assumeTrue(HostTest.isX86);
        TestRunner.run("pidigits.gcc-4", new String[]{"850"}, "", PIDIGITS, "", 0);
    }
}
