package co.edu.uniquindio.ingesis.restful.mappers;

import co.edu.uniquindio.ingesis.restful.domain.Comment;
import co.edu.uniquindio.ingesis.restful.dtos.CommentResponse;

import co.edu.uniquindio.ingesis.restful.dtos.CommentPostRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.JAKARTA_CDI)
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "project", ignore = true)
    Comment toComment(CommentPostRequest dto);

    CommentResponse toCommentResponse(Comment comment);
}