use std::path::Path;

use filesize::PathExt;

pub(super) fn size_on_disk(path_string: String) -> u64 {
    let path = Path::new(&path_string);
    path.size_on_disk().unwrap_or_default()
}
