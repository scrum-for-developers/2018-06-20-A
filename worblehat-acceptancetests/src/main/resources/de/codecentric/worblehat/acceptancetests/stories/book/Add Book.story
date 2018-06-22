Meta:
@themes Book

Narrative:
In order to add new books to the library
As a librarian
I want to add books through the website

Scenario:

Given an empty library
When a librarian adds a book with title <title>, author <author>, edition <edition>, year <year> and isbn <isbn>
And a librarian adds a book with title <title2>, author <author2>, edition <edition2>, year <year> and isbn <isbn>
Then the booklist contains a book with values title <title>, author <author>, year <year>, edition <edition>, isbn <isbn>
And the library contains <copies> of the book with <isbn>

Examples:
 
| isbn       | author           | title     | edition   | year  | author2          | edition2 | title2   | copies |
| 0552131075 | Terry Pratchett  | Sourcery  | 1         | 1989  |                  |          |          |    1   |
| 0552131075 | Terry Pratchett  | Sourcery  | 1         | 1989  | Terry Pratchett  | 1        | Sourcery |    2   |
| 0552131075 | Terry Pratchett  | Sourcery  | 1         | 1989  | Jerry Pratchett  | 1        | Sourcery |    1   |
| 0552131075 | Terry Pratchett  | Sourcery  | 1         | 1989  | Terry Pratchett  | 2        | Sourcery |    1   |
| 0552131075 | Terry Pratchett  | Sourcery  | 1         | 1989  | Terry Pratchett  | 1        | Mastery  |    1   |






