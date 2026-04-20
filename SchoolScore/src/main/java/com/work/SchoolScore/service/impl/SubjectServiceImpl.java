package com.work.SchoolScore.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.SchoolScore.dao.SubjectDAO;
import com.work.SchoolScore.dto.subject.SubjectRequest;
import com.work.SchoolScore.model.Subject;
import com.work.SchoolScore.service.SubjectService;

//這是 Service 的 實作
@Service
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectDAO dao;

	public SubjectServiceImpl(SubjectDAO dao) {
		this.dao = dao;
	}

	// 尋找全部
	@Override
	public List<Subject> findAll() {
		return dao.findAll();

	}

	// 特定 ID 搜尋
	@Override
	public Optional<Subject> findById(Long id) {
		return dao.findById(id);
	}

	// 新增
	@Override
	public Subject save(SubjectRequest req) {

		Subject sub = new Subject();
		sub.setName(req.name());

		System.out.println(sub.toString());
		return dao.save(sub);
	}


	// 更新
	@Override
	public Optional<Subject> update(Long id, SubjectRequest req) {

		var result = dao.findById(id).map(s -> {
			s.setName(req.name());
			return dao.save(s);
		}).orElse(null);

		return Optional.ofNullable(result);

		// 建議增加對 ID 的非空檢查
//		if (entity.getId() == null)
//			return null;
//
//		return dao.findById(entity.getId()).map(existing -> {
//			System.out.println("更新前: " + existing);
//			System.out.println("更新內容: " + entity);
//			return dao.save(entity);
//		}).orElse(null); // 若找不到該 ID，返回 null
	}

	// 刪除
	@Override
	public boolean delete(Long id) {

		return dao.findById(id).map(o -> {
			dao.delete(o);
			return true;
		}).orElse(false);
	}
	

//	@Override
//	public void delete(Long id) {
//		dao.deleteById(id);
//	}


}
