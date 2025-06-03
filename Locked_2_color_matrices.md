# 🔒 Locked Feedback Deduction Matrix for 2-Color Guesses (A, A, B, B)

Each matrix applies to a guess of the form `(A, A, B, B)` where A ≠ B.

Symbols:
- `✓` — Confirmed in code and in position
- `?` — Possibly in code/position
- `X` — Ruled out

---

### Feedback (0, 0)
| Color | In Code? | Pos 0 | Pos 1 | Pos 2 | Pos 3 |
|-------|----------|--------|--------|--------|--------|
| A     | X        | X      | X      | X      | X      |
| B     | X        | X      | X      | X      | X      |

_All guessed colors eliminated completely._

---

### Feedback (0, 1)
| Color | In Code? | Pos 0 | Pos 1 | Pos 2 | Pos 3 |
|-------|----------|--------|--------|--------|--------|
| A     | ?        | X      | X      | ?      | ?      |
| B     | ?        | ?      | ?      | X      | X      |

_Exactly one color is in the code in the wrong position; the other is not in the code. No way to know which color it is. The guessed positions are eliminated for both colors._

---

### Feedback (0, 2)
| Color | In Code? | Pos 0 | Pos 1 | Pos 2 | Pos 3 |
|-------|----------|--------|--------|--------|--------|
| A     | ✓        | X      | X      | ?      | ?      |
| B     | ✓        | ?      | ?      | X      | X      |

_Two white pegs. Both A and B are in the code, in wrong positions._

---

### Feedback (1, 0)
| Color | In Code? | Pos 0 | Pos 1 | Pos 2 | Pos 3 |
|-------|----------|--------|--------|--------|--------|
| A     | ?        | ?      | ?      | X      | X      |
| B     | ?        | X      | X      | ?      | ?      |

_Exactly one peg is correct in both color and position. One of the two colors is in the code in exactly one of the guessed positions; the other color is not in the code at all. The non-matching positions are eliminated for both colors._

---

### Feedback (1, 1)
| Color | In Code? | Pos 0 | Pos 1 | Pos 2 | Pos 3 |
|-------|----------|--------|--------|--------|--------|
| A     | ✓        | ?      | ?      | ?      | ?      |
| B     | ✓        | ?      | ?      | ?      | ?      |

_One black, one white — both colors in code. All positions possible._

---

### Feedback (2, 0)
| Color | In Code? | Pos 0 | Pos 1 | Pos 2 | Pos 3 |
|-------|----------|--------|--------|--------|--------|
| A     | ✓        | ?      | ?      | X      | X      |
| B     | ✓        | X      | X      | ?      | ?      |

_Exactly two pegs are correct in both color and position. Since the secret uses only distinct colors, one correct peg must be from A and one from B. The other positions for each color are excluded._
