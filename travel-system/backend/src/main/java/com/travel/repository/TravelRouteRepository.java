package com.travel.repository;

import com.travel.entity.TravelRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TravelRouteRepository extends JpaRepository<TravelRoute, Long> {

    List<TravelRoute> findByDestinationContaining(String destination);

    @Query("SELECT r FROM TravelRoute r WHERE r.routeName LIKE %:keyword% OR r.destination LIKE %:keyword%")
    List<TravelRoute> searchByKeyword(@Param("keyword") String keyword);

    List<TravelRoute> findByStockGreaterThan(Integer stock);
}
