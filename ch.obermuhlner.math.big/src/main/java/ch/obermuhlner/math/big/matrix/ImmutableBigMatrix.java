package ch.obermuhlner.math.big.matrix;

import ch.obermuhlner.math.big.matrix.internal.MatrixUtils;
import ch.obermuhlner.math.big.matrix.internal.dense.DenseImmutableBigMatrix;
import ch.obermuhlner.math.big.matrix.internal.lamdba.LambdaImmutableBigMatrix;
import ch.obermuhlner.math.big.matrix.internal.lamdba.LambdaTransformationImmutableBigMatrix;
import ch.obermuhlner.math.big.matrix.internal.sparse.SparseImmutableBigMatrix;

import java.math.BigDecimal;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface ImmutableBigMatrix extends BigMatrix {

    static ImmutableBigMatrix matrix(BigMatrix matrix) {
        return matrix(matrix.rows(), matrix.columns(), (row, column) -> matrix.get(row, column));
    }

    static ImmutableBigMatrix matrix(int rows, int columns) {
        return matrix(rows, columns, new BigDecimal[0]);
    }

    static ImmutableBigMatrix matrix(int rows, int columns, double... values) {
        return matrix(rows, columns, MatrixUtils.toBigDecimal(values));
    }

    static ImmutableBigMatrix matrix(int rows, int columns, BigDecimal... values) {
        if (MatrixUtils.preferSparseMatrix(rows, columns, values)) {
            return sparseMatrix(rows, columns, values);
        } else {
            return denseMatrix(rows, columns, values);
        }
    }

    static ImmutableBigMatrix matrix(int rows, int columns, CoordValue... values) {
        if (MatrixUtils.preferSparseMatrix(rows, columns, values)) {
            return sparseMatrix(rows, columns, values);
        } else {
            return denseMatrix(rows, columns, values);
        }
    }

    static ImmutableBigMatrix matrix(int rows, int columns, BiFunction<Integer, Integer, BigDecimal> valueFunction) {
        if (MatrixUtils.preferSparseMatrix(rows, columns, valueFunction)) {
            return sparseMatrix(rows, columns, valueFunction);
        } else {
            return denseMatrix(rows, columns, valueFunction);
        }
    }

    static ImmutableBigMatrix denseMatrix(BigMatrix matrix) {
        return denseMatrix(matrix.rows(), matrix.columns(), (row, column) -> matrix.get(row, column));
    }

    static ImmutableBigMatrix denseMatrix(int rows, int columns) {
        return denseMatrix(rows, columns, new BigDecimal[0]);
    }

    static ImmutableBigMatrix denseMatrix(int rows, int columns, double... values) {
        return denseMatrix(rows, columns, MatrixUtils.toBigDecimal(values));
    }

    static ImmutableBigMatrix denseMatrix(int rows, int columns, BigDecimal... values) {
        return new DenseImmutableBigMatrix(rows, columns, values);
    }

    static ImmutableBigMatrix denseMatrix(int rows, int columns, CoordValue... values) {
        return new DenseImmutableBigMatrix(rows, columns, values);
    }

    static ImmutableBigMatrix denseMatrix(int rows, int columns, BiFunction<Integer, Integer, BigDecimal> valueFunction) {
        return new DenseImmutableBigMatrix(rows, columns, valueFunction);
    }

    static ImmutableBigMatrix identityMatrix(int size) {
        return new LambdaImmutableBigMatrix(size, size, (row, column) -> {
            return row == column ? BigDecimal.ONE : BigDecimal.ZERO;
        });
    }

    static ImmutableBigMatrix sparseMatrix(BigMatrix matrix) {
        return sparseMatrix(matrix.rows(), matrix.columns(), (row, column) -> matrix.get(row, column));
    }

    static ImmutableBigMatrix sparseMatrix(int rows, int columns) {
        return sparseMatrix(rows, columns, new BigDecimal[0]);
    }

    static ImmutableBigMatrix sparseMatrix(int rows, int columns, double... values) {
        return sparseMatrix(rows, columns, MatrixUtils.toBigDecimal(values));
    }

    static ImmutableBigMatrix sparseMatrix(int rows, int columns, BigDecimal... values) {
        return new SparseImmutableBigMatrix(rows, columns, values);
    }

    static ImmutableBigMatrix sparseMatrix(int rows, int columns, CoordValue... values) {
        return new SparseImmutableBigMatrix(rows, columns, values);
    }

    static ImmutableBigMatrix sparseMatrix(int rows, int columns, BiFunction<Integer, Integer, BigDecimal> valueFunction) {
        return new SparseImmutableBigMatrix(rows, columns, valueFunction);
    }

    static ImmutableBigMatrix lambdaMatrix(int rows, int columns, BiFunction<Integer, Integer, BigDecimal> valueFunction) {
        return new LambdaImmutableBigMatrix(rows, columns, valueFunction);
    }

    static ImmutableBigMatrix lambdaMatrix(BigMatrix matrix, int rows, int columns, Function<Coord, Coord> transformationFunction) {
        return new LambdaTransformationImmutableBigMatrix(matrix, rows, columns, transformationFunction);
    }
}
