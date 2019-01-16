package msk.android.academy.javatemplate.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import msk.android.academy.javatemplate.network.StepsItemDTO;
import msk.android.academy.javatemplate.room.StepsItemDB;

public class Mapper {

    public static List<StepsItemDB> DtoToDb(List<StepsItemDTO> listDto) {

        final List<StepsItemDB> list = new ArrayList<>();

        for (StepsItemDTO item_dto : listDto) {
            list.add(
                    new StepsItemDB(
                            item_dto.getDate(),
                            item_dto.getWalk(),
                            item_dto.getAerobic(),
                            item_dto.getRun()
                    )
            );
            Log.d("room", "items converted: " + item_dto.getDate() + " | "
                    + "[ " + item_dto.getWalk() + ", " + item_dto.getAerobic() + ", " + item_dto.getRun() + " ]");
        }
        return list;
    }

}
