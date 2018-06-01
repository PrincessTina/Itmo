package all.controllers;


import all.entity.Point;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "points", path = "/points")
public interface PointsRepository extends CrudRepository<Point, Integer> {
  List<Point> findByUserid(@Param("userid") int userid);
}
