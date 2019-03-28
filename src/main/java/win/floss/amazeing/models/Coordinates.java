package win.floss.amazeing.models;

import java.util.Objects;

public class Coordinates {
    private int rowIndex;
    private int columnIndex;

    public Coordinates(int rowIndex, int columnIndex) {
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return rowIndex == that.rowIndex &&
               columnIndex == that.columnIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowIndex, columnIndex);
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }
}
