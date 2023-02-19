package com.diskusage.domain.usecases.chart.item.arc

import com.diskusage.domain.model.chart.Arc
import com.diskusage.domain.model.path.PathRelationship
import com.diskusage.domain.usecases.path.GetPathRelationship
import com.diskusage.domain.usecases.path.GetRoot
import com.diskusage.domain.usecases.path.GetSizeOnDisk
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
    private val getRoot: GetRoot,
    private val getPathRelationship: GetPathRelationship,
    private val getSizeOnDisk: GetSizeOnDisk
) {
    operator fun invoke(
        path: Path,
        fromPath: Path = getRoot(path)
    ) = when (getPathRelationship(path, fromPath)) {
        PathRelationship.Identity, PathRelationship.Descendant -> 360f
        PathRelationship.Unrelated, PathRelationship.Sibling -> 0f
        PathRelationship.Ancestor -> (getSizeOnDisk(path).toDouble() / getSizeOnDisk(fromPath).toDouble())
            .takeIf(Double::isFinite)
            ?.times(360)
            ?.toFloat()
            ?: 0f
    }
}
