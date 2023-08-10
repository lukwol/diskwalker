
#include <jni.h>
/* Header for class com_diskwalker_libraries_support_SupportJni */

#ifndef _Included_com_diskwalker_libraries_support_SupportJni
#define _Included_com_diskwalker_libraries_support_SupportJni
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_diskwalker_libraries_support_SupportJni
 * Method:    sizeOnDisk
 * Signature: (Ljava/lang/String;)J
 */
JNIEXPORT jlong JNICALL Java_com_diskwalker_libraries_support_SupportJni_sizeOnDisk
  (JNIEnv *, jclass, jstring);

/*
 * Class:     com_diskwalker_libraries_support_SupportJni
 * Method:    serializedSystemInfo
 * Signature: ()[B
 */
JNIEXPORT jbyteArray JNICALL Java_com_diskwalker_libraries_support_SupportJni_serializedSystemInfo
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
