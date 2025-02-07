package com.refs.lombokex.lombokdto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface PersonUserLombokMapper {
	PersonUserLombokMapper INSTANCE = Mappers.getMapper(PersonUserLombokMapper.class);

	PersonLombok personToUser(UserLombok source);

	UserLombok userToPerson(PersonLombok source);
}
