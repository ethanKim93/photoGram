package com.cos.photogramstart.domain.subscribe;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor // 전체 생성자
@NoArgsConstructor // 빈 생성자
@Data
@Entity // DB에 테이블 생성
@Table(uniqueConstraints = {
		@UniqueConstraint(
				name = "subscribe_uk",
				columnNames = {
						"fromUserId","toUserId"
				}
				)
}) // 제약 조건
public class Subscribe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호 증가 전략이 데이터베이스를 따라간다
	private int id;

	@JoinColumn(name = "fromUserId") // 이렇게 컬럼명 생성
	@ManyToOne
	private User fromUser;

	@JoinColumn(name = "toUserId")
	@ManyToOne
	private User toUser;

	private LocalDateTime createDate;

	@PrePersist // 디비에 Insert되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
