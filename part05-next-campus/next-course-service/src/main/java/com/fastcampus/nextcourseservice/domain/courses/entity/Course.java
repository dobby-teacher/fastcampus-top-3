package com.fastcampus.nextcourseservice.domain.courses.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @Column(name = "instructor_id", nullable = false)
    private Long instructorId;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @OneToMany(mappedBy = "course")
    @JsonBackReference
    private List<CourseSession> sessions;

    @OneToMany(mappedBy = "course")
    @JsonBackReference
    private List<CourseRating> ratings;

    public Course(Long courseId) {
        this.id = courseId;
    }
}
