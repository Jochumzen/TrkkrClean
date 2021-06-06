package com.trkkr.trkkrclean.api.overpass.way

import com.trkkr.trkkrclean.domain.DomainMapper
import com.trkkr.trkkrclean.domain.OsmWay

class OverpassWayDtoMapper : DomainMapper<OverpassWayDto, List<OsmWay>> {

    override fun mapToDomainModel(model: OverpassWayDto): List<OsmWay> {
        return model.elements.map { mapWayDtoElementToWay(it) }
    }

    private fun mapWayDtoElementToWay(element: OverpassWayDto.Element): OsmWay {
        return OsmWay(element.type, element.id, element.timestamp, element.version, element.changeset, element.user, element.uid, element.nodes, element.tags)
    }

    override fun mapFromDomainModel(domainModel: List<OsmWay>): OverpassWayDto {
        TODO("Not yet implemented")
    }
}