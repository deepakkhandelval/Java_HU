package com.hashedin.hu22;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookDTO {
    public int movieId;
    public int movieTheaterId;
    public int NoOfTicket;
}
