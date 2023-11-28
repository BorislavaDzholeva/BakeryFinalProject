package BakeryProject.demo.service;
import BakeryProject.demo.models.DTO.CreateReviewDTO;
import BakeryProject.demo.models.entity.Review;
import BakeryProject.demo.models.entity.UserEntity;
import BakeryProject.demo.models.enums.RoleEnum;
import BakeryProject.demo.repository.ReviewRepository;
import BakeryProject.demo.repository.UserRepository;
import BakeryProject.demo.service.impl.ReviewServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {
    @Mock
    private ReviewRepository mockReviewRepository;
    @Mock
    private UserRepository mockUserRepository;
    @Spy
    private ModelMapper modelMapper;
    @Captor
    private ArgumentCaptor<Review> reviewArgumentCaptor;
    private ReviewServiceImpl serviceToTest;
    private UserEntity createTestUser;
    private Review testReview;
    private CreateReviewDTO testCreateReviewDTO;



    @BeforeEach
    void setUp() {
        serviceToTest = new ReviewServiceImpl(mockReviewRepository, mockUserRepository, modelMapper);
        createTestUser = new UserEntity() {
            {
                setId(1L);
                setFirstName("firstName");
                setLastName("lastName");
                setPassword("1234567");
                setEmail("firstName@abv.bg");
                setUsername("user");
                setRole(RoleEnum.valueOf("User"));
            }
        };
        testCreateReviewDTO = new CreateReviewDTO() {
            {
                setCreator(createTestUser);
                setMessage("test message");
                setReviewDate(LocalDateTime.now());

            }
        };

        testReview = new Review() {

            {   setId(1L);
                setCreator(createTestUser);
                setMessage("test message");
                setReviewDate(LocalDateTime.now());
            }
        };

    }

    @Test
    void testCreateReview() {
        when(mockUserRepository.findByUsername("user")).thenReturn(Optional.of(createTestUser));
        serviceToTest.createReview(testCreateReviewDTO, "user");
        Mockito.verify(mockReviewRepository).save(reviewArgumentCaptor.capture());
        Assertions.assertEquals("test message", reviewArgumentCaptor.getValue().getMessage());
        Assertions.assertEquals("user", reviewArgumentCaptor.getValue().getCreator().getUsername());
        Assertions.assertTrue(reviewArgumentCaptor.getValue().getReviewDate().isBefore(LocalDateTime.now()));
    }

    @Test
    void testApproveReview() {
        when(mockReviewRepository.findById(1L)).thenReturn(Optional.of(testReview));
        serviceToTest.approveReview(1L);
        Mockito.verify(mockReviewRepository).save(reviewArgumentCaptor.capture());
    }

    @Test
    void testRemoveReviewById() {
        serviceToTest.removeReviewById(1L);
        Mockito.verify(mockReviewRepository).deleteById(1L);
        Assertions.assertTrue(mockReviewRepository.findById(1L).isEmpty());
    }

    @Test
    void testGetAllReviews() {
        when(mockReviewRepository.findAll()).thenReturn(List.of(testReview));
        Assertions.assertEquals(1, serviceToTest.getAllReviews().size());
    }

    @Test
    void testGetApprovedReviews() {
        when(mockReviewRepository.findAllByIsApprovedTrue()).thenReturn(List.of(testReview));
        Assertions.assertEquals(1, serviceToTest.getApprovedReviews().size());
    }
}
