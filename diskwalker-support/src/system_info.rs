use protobuf::{EnumOrUnknown, Message};
use sysinfo::{Disk, DiskExt, System, SystemExt};

use crate::system_info::{
    disk::{DiskInfoDto, DiskTypeDto},
    system::SystemInfoDto,
};

include!(concat!(env!("OUT_DIR"), "/protos/mod.rs"));

pub(super) fn serialized_system_info() -> Vec<u8> {
    let mut system = System::new();
    system.refresh_disks_list();

    let disks = system
        .disks()
        .iter()
        .map(disk_info)
        .collect::<Vec<DiskInfoDto>>();

    let mut system_info = SystemInfoDto::new();
    system_info.disks = disks;
    system_info.write_to_bytes().unwrap()
}

fn disk_info(disk: &Disk) -> DiskInfoDto {
    let mut disk_info = DiskInfoDto::new();
    disk_info.name = disk.name().to_string_lossy().to_string();
    disk_info.mount_point = disk.mount_point().to_string_lossy().to_string();
    disk_info.total_space = disk.total_space();
    disk_info.available_space = disk.available_space();
    disk_info.is_removable = disk.is_removable();
    disk_info.disk_type = EnumOrUnknown::new(match disk.type_() {
        sysinfo::DiskType::HDD => DiskTypeDto::HDD,
        sysinfo::DiskType::SSD => DiskTypeDto::SSD,
        sysinfo::DiskType::Unknown(_) => DiskTypeDto::Unknown,
    });
    disk_info.file_system = std::str::from_utf8(disk.file_system())
        .map(|s| s.to_string())
        .unwrap_or_default();
    disk_info
}
