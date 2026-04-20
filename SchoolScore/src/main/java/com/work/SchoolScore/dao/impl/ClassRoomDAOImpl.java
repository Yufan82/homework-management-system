package com.work.SchoolScore.dao.impl;

import org.springframework.stereotype.Repository;

import com.work.SchoolScore.dao.ClassRoomDAO;
import com.work.SchoolScore.model.ClassRoom;

@Repository
public class ClassRoomDAOImpl extends BaseDAOImpl<ClassRoom> implements ClassRoomDAO {

			public ClassRoomDAOImpl() {
				save(create(1L, "三年甲班", 113, 1, true));
				save(create(2L, "三年乙班", 113, 1, false));
			}
	
//			public List<ClassRoom> CLASS_ROOMS = List.of(
//		        create(1L, "三年甲班", 113, 1, true),
//		        create(2L, "三年乙班", 113, 1, false)
//		    );

		    private static ClassRoom create(Long id, String name, int year, int semester, boolean active) {
		        ClassRoom c = new ClassRoom();
		        c.setId(id);
		        c.setName(name);
		        c.setSchoolYear(year);
		        c.setSemester(semester);
		        c.setActive(active);
		        return c;
		    }

			@Override
			protected void setId(ClassRoom entity, Long id) {
				entity.setId(id);
			}

			@Override
			protected Long getId(ClassRoom entity) {
				return entity.getId();
			}
	
	

}
