package pl.mg.search.gateway.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

/**
 * Use case tests for composite search in the distributed system.
 * Tested approaches:
 * - 1 - selective data replication
 * - 2 - synchronous call to all services and joining results
 * - 3 - search between views in the database - approach simulating usage of different schemas in each microservice
 *
 * @author macgzi
 */
@RestController(value = "/search")
@Slf4j
public class SearchController {






}
