package com.trkkr.trkkrclean.api.overpass.node

import com.trkkr.trkkrclean.domain.DomainMapper
import com.trkkr.trkkrclean.domain.OsmNode

class OverpassNodeDtoMapper : DomainMapper<OverpassNodeDto, OsmNode> {
    override fun mapToDomainModel(model: OverpassNodeDto): OsmNode {
        return OsmNode(mapElementsToDomainModel(model.elements))
    }

    private fun mapElementToDomainModel(model: OverpassNodeDto.Element): OsmNode.Element {
        return OsmNode.Element(model.type, model.id, model.lat, model.lon, model.timestamp, model.version, model.changeset, model.user, model.uid, model.tags)
    }

    private fun mapElementsToDomainModel(model: List<OverpassNodeDto.Element>): List<OsmNode.Element> {
        return model.map { mapElementToDomainModel(it) }
    }

    override fun mapFromDomainModel(domainModel: OsmNode): OverpassNodeDto {
        TODO("Not yet implemented")
    }

}
