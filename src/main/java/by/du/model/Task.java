package by.du.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tbl_todo_task")
public class Task implements Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "task_desc")
    private String desc;
    @Column(name = "task_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "task_date")
    private LocalDate date;
}
