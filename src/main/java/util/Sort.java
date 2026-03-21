package util;

import project2.Section;

public class Sort {

    public static <E extends Comparable<E>> void sort(List<E> list) {

        for (int i = 0; i < list.size() - 1; i++) {

            int minIndex = i;

            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j).compareTo(list.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                E temp = list.get(i);
                list.set(i, list.get(minIndex));
                list.set(minIndex, temp);
            }
        }
    }


    public static void sortSectionsByCourse(List<Section> list) {

        for (int i = 0; i < list.size() - 1; i++) {

            int minIndex = i;

            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j).compareByCourse(list.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                Section temp = list.get(i);
                list.set(i, list.get(minIndex));
                list.set(minIndex, temp);
            }
        }
    }

    public static void sortSectionsByClassroom(List<Section> list) {

        for (int i = 0; i < list.size() - 1; i++) {

            int minIndex = i;

            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(j).compareByClassroom(list.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                Section temp = list.get(i);
                list.set(i, list.get(minIndex));
                list.set(minIndex, temp);
            }
        }
    }
}
