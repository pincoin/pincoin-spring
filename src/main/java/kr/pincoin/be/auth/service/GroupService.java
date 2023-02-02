package kr.pincoin.be.auth.service;

import kr.pincoin.be.auth.domain.Group;
import kr.pincoin.be.auth.repository.GroupRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class GroupService {
    private final GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Transactional
    public List<Group> listGroups() {
        return groupRepository.findAll();
    }
}
