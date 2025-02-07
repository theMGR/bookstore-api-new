package com.refs.lombokex;

import com.refs.lombokex.lombokdto.PersonLombok;
import com.refs.lombokex.lombokdto.PersonUserLombokMapper;
import com.refs.lombokex.lombokdto.UserLombok;

//Common Lombok Annotations:
//@Getter: Generates getter methods for all fields.
//@Setter: Generates setter methods for all fields.
//@ToString: Generates a toString() method.
//@EqualsAndHashCode: Generates equals() and hashCode() methods.
//@NoArgsConstructor: Generates a no-arguments constructor.
//@AllArgsConstructor: Generates a constructor with all fields.
//@RequiredArgsConstructor
//@Data: A shortcut for @Getter, @Setter, @ToString, @EqualsAndHashCode, and @RequiredArgsConstructor.
//@Slf4j: Generates a logger instance.
//@Builder

public class LombokExample implements PersonUserLombokMapper {

	public void lombokMapperEx() {
		PersonLombok personLombok = new PersonLombok();
		personLombok.setAge(10);
		personLombok.setName("Name 1");

	}

	@Override
	public PersonLombok personToUser(UserLombok source) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserLombok userToPerson(PersonLombok source) {
		// TODO Auto-generated method stub
		return null;
	}

}






