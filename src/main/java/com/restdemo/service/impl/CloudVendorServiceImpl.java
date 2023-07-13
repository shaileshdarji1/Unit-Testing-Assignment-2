package com.restdemo.service.impl;

import com.restdemo.exception.CloudVendorNotFoundException;
import com.restdemo.repository.CloudVendorRepository;
import com.restdemo.service.CloudVendorService;
import com.restdemo.entity.CloudVendor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CloudVendorServiceImpl implements CloudVendorService {

    CloudVendorRepository cloudVendorRepository;

    public CloudVendorServiceImpl(CloudVendorRepository cloudVendorRepository) {
        this.cloudVendorRepository = cloudVendorRepository;
    }

    @Override
    public String createCloudVendor(CloudVendor cloudVendor) {
        // More Business Logic

        CloudVendor save = cloudVendorRepository.save(cloudVendor);
        if(save==cloudVendor) {
            return "Success";
        }
        else
        {
            return "Failed";
        }
    }

    @Override
    public String updateCloudVendor(CloudVendor cloudVendor) {
        // More Business Logic
        CloudVendor save = cloudVendorRepository.save(cloudVendor);
        if(save==cloudVendor) {
            return "Success";
        }
        else
        {
            return "Failed";
        }

    }

    @Override
    public String deleteCloudVendor(String cloudVendorId) {
        // More Business Logic
        cloudVendorRepository.deleteById(cloudVendorId);
        return "Success";
    }

    @Override
    public CloudVendor getCloudVendor(String cloudVendorId) {
        // More Business Logic
        CloudVendor cloudVendor = cloudVendorRepository.findById(cloudVendorId).orElse(null);
        if(cloudVendor == null){
            throw new CloudVendorNotFoundException("Cloud Vendor does not exist");
        }
        return cloudVendorRepository.findById(cloudVendorId).get();
    }

    @Override
    public List<CloudVendor> getAllCloudVendors() {
        // More Business Logic
        if(cloudVendorRepository.findAll().isEmpty())
            throw new CloudVendorNotFoundException("Cloud Vendor does not exist");
        return cloudVendorRepository.findAll();
    }

    @Override
    public List<CloudVendor> getByVendorName(String vendorName)
    {
        List<CloudVendor> byVendorName = cloudVendorRepository.findByVendorName(vendorName);
        if(byVendorName.isEmpty()){
            throw new CloudVendorNotFoundException("Cloud Vendors does not exits");
        }
        return byVendorName;
    }
}
