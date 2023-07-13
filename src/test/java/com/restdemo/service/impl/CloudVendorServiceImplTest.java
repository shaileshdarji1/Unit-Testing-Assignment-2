package com.restdemo.service.impl;

import com.restdemo.entity.CloudVendor;
import com.restdemo.exception.CloudVendorNotFoundException;
import com.restdemo.repository.CloudVendorRepository;
import com.restdemo.service.CloudVendorService;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class CloudVendorServiceImplTest {

    @Mock
    private CloudVendorRepository cloudVendorRepository;

    private CloudVendorService cloudVendorService;
    AutoCloseable autoCloseable;
    CloudVendor cloudVendor;
    CloudVendor cloudVendor1;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        cloudVendorService = new CloudVendorServiceImpl(cloudVendorRepository);
        cloudVendor = new CloudVendor("1","Amazon","USA","8154990463");
        cloudVendor1 = new CloudVendor("2","GCP","UK","8154990463");
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Nested
    class SaveCloudVendor {
        @Test
        void createCloudVendor_Success() {

            when(cloudVendorRepository.save(cloudVendor)).thenReturn(cloudVendor);
            assertThat(cloudVendorService.createCloudVendor(cloudVendor)).isEqualTo("Success");

        }

        @Test
        void createCloudVendor_Failed() {

            when(cloudVendorRepository.save(cloudVendor)).thenReturn(null);
            assertThat(cloudVendorService.createCloudVendor(cloudVendor)).isEqualTo("Failed");
        }
    }


    @Nested
    class UpdateCloudVendor {

        @Test
        void updateCloudVendor_Success() {
            when(cloudVendorRepository.save(cloudVendor)).thenReturn(cloudVendor);
            assertThat(cloudVendorService.updateCloudVendor(cloudVendor)).isEqualTo("Success");
        }

        @Test
        void updateCloudVendor_Failed() {
            when(cloudVendorRepository.save(cloudVendor1)).thenReturn(cloudVendor);
            assertThat(cloudVendorService.updateCloudVendor(cloudVendor)).isEqualTo("Failed");
        }
    }

    @Nested
    class GetCloudVendor {
        @Test
        void getCloudVendor_Success() {
            when(cloudVendorRepository.findById("1")).thenReturn(Optional.ofNullable(cloudVendor));
            AssertionsForClassTypes.assertThat(cloudVendorService.getCloudVendor("1").getVendorName())
                    .isEqualTo(cloudVendor.getVendorName());
        }
        @Test
        void getCloudVendor_Failed() {
            when(cloudVendorRepository.findById("1")).thenReturn(Optional.ofNullable(null));
            assertThrows(CloudVendorNotFoundException.class,()->cloudVendorService.getCloudVendor("1"));
        }
    }

    @Nested
    class GetAllCloudVendors {
        @Test
        void getAllCloudVendors_Success() {
            when(cloudVendorRepository.findAll()).thenReturn(new ArrayList<CloudVendor>(Collections.singleton(cloudVendor)));
        assertThat(cloudVendorService.getAllCloudVendors().get(0).getVendorPhoneNumber())
                    .isEqualTo(cloudVendor.getVendorPhoneNumber());

        }

        @Test
        void getAllCloudVendors_Failed(){
            when(cloudVendorRepository.findAll()).thenReturn(new ArrayList<>());
            assertThrows(CloudVendorNotFoundException.class,()->cloudVendorService.getAllCloudVendors());
        }
    }

    @Nested
    class GetByVendorName {
        @Test
        void getByVendorName_Success() {
            when(cloudVendorRepository.findByVendorName("Amazon")).thenReturn(new ArrayList<CloudVendor>(Collections.singleton(cloudVendor)));
            assertThat(cloudVendorService.getByVendorName("Amazon").get(0).getVendorId())
                    .isEqualTo(cloudVendor.getVendorId());
        }
        @Test
        void getByVendorName_Failed() {
            when(cloudVendorRepository.findByVendorName("Amazon")).thenReturn(new ArrayList<>());
            assertThrows(CloudVendorNotFoundException.class,()->cloudVendorService.getByVendorName("Amazon"));
        }
    }
    @Test
    void deleteCloudVendor() {
        mock(CloudVendor.class);
        mock(CloudVendorRepository.class, Mockito.CALLS_REAL_METHODS);
        doAnswer(Answers.CALLS_REAL_METHODS).when(cloudVendorRepository).deleteById(any());
        assertThat(cloudVendorService.deleteCloudVendor("1")).isEqualTo("Success");
    }

}