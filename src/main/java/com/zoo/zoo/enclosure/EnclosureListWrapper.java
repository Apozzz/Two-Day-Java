package com.zoo.zoo.enclosure;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnclosureListWrapper {

    private List<EnclosureRequest> enclosures;

}
