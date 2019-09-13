package org.jboss.xavier.integrations.jpa.repository;

import org.jboss.xavier.analytics.pojo.output.AnalysisModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisRepository extends JpaRepository<AnalysisModel, Long>
{

    Page<AnalysisModel> findByOwnerAndReportNameIgnoreCaseContaining(String owner, String filterText, Pageable pageable);

    Page<AnalysisModel> findAllByOwner(String owner, Pageable pageable);

    AnalysisModel findByOwnerAndId(String owner, Long id);

    Integer countByOwner(String owner);

}
