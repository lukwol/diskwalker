package com.diskwalker.libraries.support;

class SupportJni {
    static native long sizeOnDisk(String path);

    static native byte[] serializedSystemInfo();
}
