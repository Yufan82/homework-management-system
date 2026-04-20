package com.work.SchoolScore.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.work.SchoolScore.dao.ClassRoomDAO;
import com.work.SchoolScore.model.ClassRoom;
import com.work.SchoolScore.service.ClassRoomService;

//這是 Service 的 實作
@Service
public class ClassRoomServiceImpl implements ClassRoomService {

	private ClassRoomDAO dao;

	public ClassRoomServiceImpl(ClassRoomDAO dao) {
		this.dao = dao;
	}

	// 尋找全部
	@Override
	public List<ClassRoom> findAll() {
		return dao.findAll();
	}

	// 特定 ID 搜尋
	@Override
	public Optional<ClassRoom> findById(Long id) {
		return Optional.ofNullable(dao.findById(id).orElse(null));
	}

	// 新增
	@Override
	public ClassRoom save(ClassRoom entity) {
		System.out.println(entity.toString());
		return dao.save(entity);
	}

	// 更新
	@Override
	public ClassRoom update(ClassRoom entity) {

		// 建議增加對 ID 的非空檢查
		if (entity.getId() == null)
			return null;

		return dao.findById(entity.getId()).map(existing -> {
			System.out.println("更新前: " + existing);
			System.out.println("更新內容: " + entity);
			return dao.save(entity);
		}).orElse(null); // 若找不到該 ID，返回 null
	}

	// 刪除
	@Override
	public void delete(Long id) {
		dao.delete(id);
	}

}
