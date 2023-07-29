
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
public class MatrixServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String matrixString = request.getParameter("matrix");
        String isIdentityString = request.getParameter("IsIdentity");
        String transposeString = request.getParameter("Transpose");

        // Check if the matrix is empty
        if (matrixString == null || matrixString.isEmpty()) {
            response.getWriter().println("Matrix is empty Please enter a matrix");
            return;
        }

        int[][] matrix = parseMatrix(matrixString,response);
        //the matrix is not square
        if (matrix ==null)
        {
            return;
        }
        if (isIdentityString != null && transposeString != null) {
            // Both Identity and Transpose are checked
            boolean isIdentity = checkIdentityMatrix(matrix);
            int[][] transpose = calculateTranspose(matrix);
            displayOutput(response, matrix, transpose, isIdentityString);
        } else if (isIdentityString != null && transposeString ==null) {
            // Only Identity is checked
            boolean isIdentity = checkIdentityMatrix(matrix);
            displayOutput(response, matrix, null, isIdentityString);
        } else if (transposeString != null && isIdentityString ==null) {
            // Only Transpose is checked
            int[][] transpose = calculateTranspose(matrix);
            displayOutput(response, matrix, transpose, null);
        } else if (isIdentityString == null && transposeString == null) {
            // Neither Identity nor Transpose is checked
            displayOutput(response, matrix, null, null);
        }
    }

    private int[][] parseMatrix(String matrixString,HttpServletResponse response) throws IOException {
        String[] rows = matrixString.split("\n");
        int size = rows.length;
        int[][] matrix = new int[size][size];

        for (int i = 0; i < size; i++) {
            String[] elements = rows[i].trim().split("\\s+");
            if (elements.length !=size)
            {
                response.getWriter().println("Matrix entered is not a square matrix");
                return null;

            }
            for (int j = 0; j < size; j++) {
                matrix[i][j] = Integer.parseInt(elements[j]);
            }
        }

        return matrix;
    }

    private boolean checkIdentityMatrix(int[][] matrix) {
        int size = matrix.length;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (i == j && matrix[i][j] != 1) {
                    return false;
                } else if (i != j && matrix[i][j] != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    private int[][] calculateTranspose(int[][] matrix) {
        int size = matrix.length;
        int[][] transpose = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                transpose[j][i] = matrix[i][j];
            }
        }

        return transpose;
    }

    private void displayOutput(HttpServletResponse response, int[][] matrix, int[][] transpose, String isIdentity)
            throws IOException {
        response.setContentType("text/html");
        response.getWriter().println("<html>");
        response.getWriter().println("<body bgcolor=\"#f0f0f0\" align=\"center\">");
        response.getWriter().println("<h1 align=\"center\"> Matrix Operations </h1>");

        if (matrix ==null)
        {
            response.getWriter().println("<h2>The Matrix is empty please enter it</h2>");
            return;
        }
        // Display the original matrix
        response.getWriter().println("<h2>Original Matrix:</h2>");
        response.getWriter().println("<pre>" + matrixToString(matrix) + "</pre>");

        // Display the transpose if available
        if (transpose != null) {
            response.getWriter().println("<h2>Transpose:</h2>");
            response.getWriter().println("<pre>" + matrixToString(transpose) + "</pre>");
        }
        //Double Check as the idenetity may not be checked
        if (isIdentity !=null)
        {
            boolean checkonIndentity=checkIdentityMatrix(matrix);
            if (checkonIndentity) {
                response.getWriter().println("<h2>Identity Matrix:</h2>");
                response.getWriter().println("<p>True</p>");
            }else {
                response.getWriter().println("<h2>Identity Matrix:</h2>");
                response.getWriter().println("<p>False</p>");
            }
        }


        response.getWriter().println("</body>");
        response.getWriter().println("</html>");
    }
//To transfer the matrix to String to be displayed
    private String matrixToString(int[][] matrix) {
        StringBuilder sb = new StringBuilder();
        int size = matrix.length;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                sb.append(matrix[i][j]).append(" ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
