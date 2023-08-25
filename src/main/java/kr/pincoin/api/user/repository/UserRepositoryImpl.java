package kr.pincoin.api.user.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import kr.pincoin.api.user.domain.QEmailAddress;
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
        QEmailAddress emailAddress = QEmailAddress.emailAddress;

        return Optional.ofNullable(queryFactory
                                           .select(Projections.fields(UserResult.class,
                                                                      user.id.as("id"),
                                                                      user.password.as("password"),
                                                                      user.username.as("username"),
                                                                      user.firstName.as("firstName"),
                                                                      user.lastName.as("lastName"),
                                                                      emailAddress.email.as("email"),
                                                                      user.superuser.as("superuser"),
                                                                      user.staff.as("staff"),
                                                                      user.active.as("active"),
                                                                      user.dateJoined.as("dateJoined"),
                                                                      user.lastLogin.as("lastLogin")))
                                           .from(user)
                                           .innerJoin(emailAddress)
                                           .on(emailAddress.user.id.eq(user.id))
                                           .where(user.username.eq(username),
                                                  emailAddress.primary.isTrue(),
                                                  emailAddress.verified.isTrue(),
                                                  user.active.isTrue())
                                           .fetchOne());
    }
}