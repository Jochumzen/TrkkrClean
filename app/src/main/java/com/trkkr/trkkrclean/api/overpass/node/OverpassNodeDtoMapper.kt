package com.trkkr.trkkrclean.api.overpass.node

import com.trkkr.trkkrclean.domain.DomainMapper
import com.trkkr.trkkrclean.domain.OsmNode

class OverpassNodeDtoMapper : DomainMapper<OverpassNodeDto, List<OsmNode>> {

    override fun mapToDomainModel(model: OverpassNodeDto): List<OsmNode> {
        return model.elements.map { mapNodeDtoElementToNode(it) }
    }

    private fun mapNodeDtoElementToNode(element: OverpassNodeDto.Element): OsmNode {
        return OsmNode(element.type, element.id, element.lat, element.lon, element.timestamp, element.version, element.changeset, element.user, element.uid, element.tags)
    }

    override fun mapFromDomainModel(domainModel: List<OsmNode>): OverpassNodeDto {
        TODO("Not yet implemented")
    }

}
