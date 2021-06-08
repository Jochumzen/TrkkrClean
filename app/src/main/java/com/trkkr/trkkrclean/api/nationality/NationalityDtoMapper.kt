package com.example.play71.network

import com.trkkr.trkkrclean.domain.DomainMapper

class NationalityDtoMapper : DomainMapper<NationalityDto, Nationality> {
    override fun mapToDomainModel(model: NationalityDto): Nationality {
        return Nationality(model.id, model.uid, model.nationality, model.language, model.capital, model.national_sport, model.flag)
    }

    override fun mapFromDomainModel(domainModel: Nationality): NationalityDto {
        return NationalityDto(domainModel.id, domainModel.uid, domainModel.nationality, domainModel.language, domainModel.capital, domainModel.national_sport, domainModel.flag)
    }
}