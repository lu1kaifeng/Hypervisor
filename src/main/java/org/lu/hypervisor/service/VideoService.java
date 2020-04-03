package org.lu.hypervisor.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.lu.hypervisor.entity.Course;
import org.lu.hypervisor.entity.Video;
import org.lu.hypervisor.repo.CourseRepo;
import org.lu.hypervisor.repo.VideoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class VideoService {
    private CourseRepo courseRepo;
    private VideoRepo videoRepo;
    private String secret;
    @Autowired
    public VideoService(@Lazy CourseRepo courseRepo, VideoRepo videoRepo, Environment env) {
        this.courseRepo = courseRepo;
        this.videoRepo = videoRepo;
        secret = env.getProperty("HMAC256.secret");
    }

    private String videoWebIdGen() {
        int count = 16;
        String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder();
        while (count-- != 0) {
            int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
            builder.append(ALPHA_NUMERIC_STRING.charAt(character));
        }
        return builder.toString();
    }

    public String save(Course course, Video video){
        String title = video.getTitle();
        video.setWebId(this.videoWebIdGen());
        video = videoRepo.save(video);
        course.getCourseVideo().add(video);
        courseRepo.save(course);
        Algorithm algorithm = Algorithm.HMAC256(this.secret);
        return JWT.create()
                .withClaim("dest",title)
                .sign(algorithm);
    }

}
