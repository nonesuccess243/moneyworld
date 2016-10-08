//package com.nbm.investment;
//
//import java.io.File;
//import org.apache.commons.io.FileUtils;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//
//@Controller
//public class UploadController
//{
//
//        @RequestMapping(value = "/upload.do", method = RequestMethod.GET)
//        public @ResponseBody String provideUploadInfo()
//        {
//                return "You can upload a file by posting to this same URL.";
//        }
//
//        @RequestMapping(value = "/upload.do", method = RequestMethod.POST)
//        public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
//           @RequestParam("file1") MultipartFile file1,@RequestParam("file2") MultipartFile file2,
//           @RequestParam("file3") MultipartFile file3,@RequestParam("file4") MultipartFile file4,
//           @RequestParam("file5") MultipartFile file5)
//        {
//                if (!file1.isEmpty())
//                {
//                        try
//                        {
//                                FileUtils.copyInputStreamToFile(file1.getInputStream(), new File("d:\\temp\\"+name+"\\", System.currentTimeMillis()+ file1.getOriginalFilename()));
//                        } catch (Exception e)
//                        {
//                        }
//                        
//                }
//                if (!file2.isEmpty())
//                {
//                        try
//                        {
//                                FileUtils.copyInputStreamToFile(file2.getInputStream(), new File("d:\\temp\\"+name+"\\", System.currentTimeMillis()+ file2.getOriginalFilename()));
//                        } catch (Exception e)
//                        {
//                        }
//                } 
//                if (!file3.isEmpty())
//                {
//                        try
//                        {
//                                FileUtils.copyInputStreamToFile(file3.getInputStream(), new File("d:\\temp\\"+name+"\\", System.currentTimeMillis()+ file3.getOriginalFilename()));
//                        } catch (Exception e)
//                        {
//                        }
//                } 
//                if (!file4.isEmpty())
//                {
//                        try
//                        {
//                                FileUtils.copyInputStreamToFile(file4.getInputStream(), new File("d:\\temp\\"+name+"\\", System.currentTimeMillis()+ file4.getOriginalFilename()));
//                        } catch (Exception e)
//                        {
//                        }
//                }
//                if (!file5.isEmpty())
//                {
//                        try
//                        {
//                                FileUtils.copyInputStreamToFile(file5.getInputStream(), new File("d:\\temp\\"+name+"\\", System.currentTimeMillis()+ file5.getOriginalFilename()));
//                        } catch (Exception e)
//                        {
//                        }
//                        
//                }
//                return "You successfully uploaded " + name + "!";
//        }
//}
