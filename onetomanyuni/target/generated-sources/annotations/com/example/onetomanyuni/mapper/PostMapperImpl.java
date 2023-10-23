package com.example.onetomanyuni.mapper;

import com.example.onetomanyuni.dto.CommentDTO;
import com.example.onetomanyuni.dto.PostDTO;
import com.example.onetomanyuni.model.Comment;
import com.example.onetomanyuni.model.Post;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-23T22:33:05+0530",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
public class PostMapperImpl implements PostMapper {

    @Override
    public PostDTO toPostDTO(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDTO postDTO = new PostDTO();

        postDTO.setId( post.getId() );
        postDTO.setTitle( post.getTitle() );
        postDTO.setDescription( post.getDescription() );
        postDTO.setComments( commentListToCommentDTOList( post.getComments() ) );

        return postDTO;
    }

    @Override
    public Post toPost(PostDTO postDTO) {
        if ( postDTO == null ) {
            return null;
        }

        Post post = new Post();

        post.setId( postDTO.getId() );
        post.setTitle( postDTO.getTitle() );
        post.setDescription( postDTO.getDescription() );
        post.setComments( commentDTOListToCommentList( postDTO.getComments() ) );

        return post;
    }

    protected CommentDTO commentToCommentDTO(Comment comment) {
        if ( comment == null ) {
            return null;
        }

        CommentDTO commentDTO = new CommentDTO();

        commentDTO.setId( comment.getId() );
        commentDTO.setText( comment.getText() );

        return commentDTO;
    }

    protected List<CommentDTO> commentListToCommentDTOList(List<Comment> list) {
        if ( list == null ) {
            return null;
        }

        List<CommentDTO> list1 = new ArrayList<CommentDTO>( list.size() );
        for ( Comment comment : list ) {
            list1.add( commentToCommentDTO( comment ) );
        }

        return list1;
    }

    protected Comment commentDTOToComment(CommentDTO commentDTO) {
        if ( commentDTO == null ) {
            return null;
        }

        Comment comment = new Comment();

        comment.setId( commentDTO.getId() );
        comment.setText( commentDTO.getText() );

        return comment;
    }

    protected List<Comment> commentDTOListToCommentList(List<CommentDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Comment> list1 = new ArrayList<Comment>( list.size() );
        for ( CommentDTO commentDTO : list ) {
            list1.add( commentDTOToComment( commentDTO ) );
        }

        return list1;
    }
}
