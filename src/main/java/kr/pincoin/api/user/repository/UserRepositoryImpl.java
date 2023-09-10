package kr.pincoin.api.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.pincoin.api.user.domain.QUser;
import kr.pincoin.api.user.dto.UserResult;

import java.util.Optional;

public class UserRepositoryImpl implements UserRepositoryQuery {
    private final JPAQueryFactory queryFactory;

    public UserRepositoryImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Optional<UserResult> findUserByUsername(String username, Boolean active) {
        QUser user = QUser.user;

        return Optional.ofNullable(queryFactory
                                           .select(Projections.fields(UserResult.class,
                                                                      user.id.as("id"),
                                                                      user.password.as("password"),
                                                                      user.username.as("username"),
                                                                      user.active.as("active")))
                                           .from(user)
                                           .where(user.username.eq(username),
                                                  user.active.isTrue())
                                           .fetchOne());
    }
}