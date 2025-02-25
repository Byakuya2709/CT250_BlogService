package com.service.service;

import com.service.exception.BadRequestException;
import com.service.exception.ResourceNotFoundException;
import com.service.exception.ResponseHandler;
import com.service.model.FeedBack;
import com.service.repository.FeedBackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FeedbackService {

    @Autowired
    private FeedBackRepository feedBackRepository;

    // Tạo feedback
    public ResponseEntity<Object> createFeedBack(FeedBack feedBack) {
        try {
            if (feedBack.getFbContent() == null || feedBack.getFbContent().isEmpty()) {
                throw new BadRequestException("Nội dung phản hồi không thể để trống.");
            }
            FeedBack createdFeedBack = feedBackRepository.save(feedBack);
            return ResponseHandler.resBuilder("Tạo phản hồi thành công.", HttpStatus.CREATED, createdFeedBack);
        } catch (BadRequestException ex) {
            return ResponseHandler.resBuilder(ex.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception ex) {
            return ResponseHandler.resBuilder(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    // Danh sách tất cả feedback
    public ResponseEntity<Object> getAllFeedBacks() {
        try {
            List<FeedBack> feedbacks = feedBackRepository.findAll();
            return ResponseHandler.resBuilder("Lấy danh sách phản hồi thành công.", HttpStatus.OK, feedbacks);
        } catch (Exception ex) {
            return ResponseHandler.resBuilder(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    // Lấy feedback theo id
    public ResponseEntity<Object> getFeedBackById(String id) {
        try {
            FeedBack feedBack = feedBackRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Không thấy bài đánh giá với id " + id));
            return ResponseHandler.resBuilder("Lấy phản hồi thành công.", HttpStatus.OK, feedBack);
        } catch (ResourceNotFoundException ex) {
            return ResponseHandler.resBuilder(ex.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception ex) {
            return ResponseHandler.resBuilder(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    // Cập nhật feedback
    public ResponseEntity<Object> updateFeedBack(String id, FeedBack details) {
        try {
            FeedBack existFeedBack = feedBackRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Không thấy bài đánh giá với id " + id));

            if (details.getFbContent() == null || details.getFbContent().isEmpty()) {
                throw new BadRequestException("Nội dung phản hồi không thể để trống.");
            }

            existFeedBack.setFbContent(details.getFbContent());
            existFeedBack.setFbCreateDate(details.getFbCreateDate());
            existFeedBack.setEventId(details.getEventId());

            FeedBack updatedFeedBack = feedBackRepository.save(existFeedBack);
            return ResponseHandler.resBuilder("Cập nhật phản hồi thành công.", HttpStatus.OK, updatedFeedBack);
        } catch (ResourceNotFoundException ex) {
            return ResponseHandler.resBuilder(ex.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (BadRequestException ex) {
            return ResponseHandler.resBuilder(ex.getMessage(), HttpStatus.BAD_REQUEST, null);
        } catch (Exception ex) {
            return ResponseHandler.resBuilder(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    // Xóa feedback theo id
    public ResponseEntity<Object> deleteFeedBack(String id) {
        try {
            FeedBack existFeedBack = feedBackRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Không thấy bài đánh giá với id " + id));

            feedBackRepository.delete(existFeedBack);
            return ResponseHandler.resBuilder("Xóa phản hồi thành công.", HttpStatus.OK, null);
        } catch (ResourceNotFoundException ex) {
            return ResponseHandler.resBuilder(ex.getMessage(), HttpStatus.NOT_FOUND, null);
        } catch (Exception ex) {
            return ResponseHandler.resBuilder(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
