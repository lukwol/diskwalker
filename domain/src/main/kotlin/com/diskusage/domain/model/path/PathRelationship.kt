package com.diskusage.domain.model.path

import java.nio.file.Path

/**
 * Relationship between two [paths][Path]
 */
enum class PathRelationship {
    /**
     * Both paths are the same.
     */
    Identity,

    /**
     * When path starts with other path
     */
    Ancestor,

    /**
     * When path is contained in other path
     */
    Descendant,

    /**
     * Both paths have the same parent
     */
    Sibling,

    /**
     * Both [paths][Path] are in [Unrelated] relationship,
     * when they are not in any other [relationship][PathRelationship]
     */
    Unrelated,
}
