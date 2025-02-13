package operations.category;

import java.util.ArrayList;

public record DeleteCategoryOp(String token, ArrayList<String> categoryIds) {
}
