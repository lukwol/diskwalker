package com.diskwalker.domain.usecases.chart.item.arc

import com.diskwalker.domain.model.chart.Arc
import com.diskwalker.domain.model.path.PathRelationship
import com.diskwalker.domain.usecases.path.GetPathRelationship
import com.diskwalker.domain.usecases.path.GetSizeOnDisk
import java.nio.file.Path

/**
 * Calculate sweep angle of the [Arc] based on given `path` starting from `fromPath`.
 *
 * To compute sweep angle it's necessary to first check the [relationship][PathRelationship]
 * between both [paths][Path].
 *
 * When both entries are [the same][PathRelationship.Identity]
 * or `fromPath` is [descendant][PathRelationship.Descendant] of `path` the result is **360f**.
 *
 * When `fromPath` is [unrelated][PathRelationship.Unrelated]
 * or [sibling][PathRelationship.Sibling] to `path`, the result is **0f**.
 *
 * When `fromPath` is an [ancestor][PathRelationship.Ancestor] of `path`
 * the result is dividing `path` size by `fromPath` size and converting it into actual angle.
 */
class GetSweepAngle(
    private val getPathRelationship: GetPathRelationship,
    private val getSizeOnDisk: GetSizeOnDisk,
) {
    operator fun invoke(
        path: Path,
        fromPath: Path,
        disk: Path,
    ) = when (getPathRelationship(path, fromPath, disk)) {
        PathRelationship.Identity, PathRelationship.Descendant, PathRelationship.Disk -> 360f
        PathRelationship.Unrelated, PathRelationship.Sibling -> 0f
        PathRelationship.Ancestor -> (getSizeOnDisk(path).toDouble() / getSizeOnDisk(fromPath).toDouble())
            .takeIf(Double::isFinite)
            ?.times(360)
            ?.toFloat()
            ?: 0f
    }
}
