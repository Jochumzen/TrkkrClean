package com.trkkr.trkkrclean.api.overpass.node

interface OsmNodeMapper <T, OsmNodeModel> {
    fun overpassNodeToOsmNode(model: T): OsmNodeModel
    fun overpassNodeFromOsmNode(osmNodeModel: OsmNodeModel): T
}