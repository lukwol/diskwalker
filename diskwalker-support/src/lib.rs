use jni::{
    JNIEnv,
    objects::{JClass, JString},
    sys::jlong,
};
use jni::objects::JObject;
use jni::sys::jbyteArray;

use crate::size_on_disk::size_on_disk;
use crate::system_info::serialized_system_info;

mod size_on_disk;
mod system_info;

#[no_mangle]
pub extern "system" fn Java_com_diskwalker_libraries_support_SupportJni_sizeOnDisk(
    mut env: JNIEnv,
    _class: JClass,
    input_path: JString,
) -> jlong {
    let path_string: String = match env.get_string(input_path.as_ref()) {
        Ok(java_string) => java_string.into(),
        Err(_) => return 0,
    };

    size_on_disk(path_string).try_into().unwrap_or_default()
}

#[no_mangle]
pub extern "system" fn Java_com_diskwalker_libraries_support_SupportJni_serializedSystemInfo(
    env: JNIEnv,
    _class: JClass,
) -> jbyteArray {
    env.byte_array_from_slice(serialized_system_info().as_ref())
        .unwrap_or_else(|_| JObject::null().into())
        .into_raw()
}
