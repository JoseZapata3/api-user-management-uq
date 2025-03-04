package co.edu.uniquindio.ingesis.restful.services;

import co.edu.uniquindio.ingesis.restful.dtos.ExecutionRequest;
import co.edu.uniquindio.ingesis.restful.dtos.ExecutionResponse;

import java.util.UUID;
public interface IExecutionService {
    ExecutionResponse executeCode(ExecutionRequest request);

    ExecutionResponse getExecutionById(UUID id);
}
