package Public;

import java.util.*;

/*
 * 有括號在內的字符串表達式運算
 * 24點運算器
 * 
 * 
 */

class stack {

	float[] stack_Num;
	char[] stack_Symbol;

	stack() {

		stack_Num = new float[100];
		stack_Symbol = new char[100];
		stack_Num[0] = 0;
		stack_Symbol[0] = 0;
	}

	float PopNum() {
		return stack_Num[(int) stack_Num[0]];
	}

	char PopChar() {
		return stack_Symbol[stack_Symbol[0]];
	}

	void Push(float p_Num) {
		this.stack_Num[0] += 1;
		this.stack_Num[(int) this.stack_Num[0]] = p_Num;
	}

	void Push(char p_Char) {
		// System.out.println((int)(this.stack_Symbol[0]));

		this.stack_Symbol[0] = (char) (this.stack_Symbol[0] + 1);
		this.stack_Symbol[(int) (this.stack_Symbol[0])] = p_Char;
	}

	void ClearLastNum() {
		this.stack_Num[(int) this.stack_Num[0]] = 0;

		this.stack_Num[0] -= 1;
	}

	void ClearLastChar() {
		this.stack_Symbol[(int) (this.stack_Symbol[0])] = 0;
		this.stack_Symbol[0] -= 1;
	}
}

public class Point24Math {
	private int result_n = 0;
	LinkedHashSet<String> ansSet=new LinkedHashSet<>();
	
	float go(float Num1, float Num2, char Syb) {
		switch (Syb) {
		case '+':
			return Num1 + Num2;
		case '-':
			return Num1 - Num2;
		case '*':
			return Num1 * Num2;
		case '/':
			return Num1 / Num2;
		}
		return 0;
	}

	int Getfirst(char Syb) {
		switch (Syb) {
		case '+':
		case '-':
			return 2;
		case '*':
		case '/':
			return 1;

		}
		return 10;
	}

	int CharToInt(char Char) {
		return Integer.valueOf(String.valueOf(Char));
	}

	public LinkedHashSet<String> findAllAns(String[] str){
		ansSet=new LinkedHashSet<>();
		change(str, 0, 3);
		
		return ansSet;
		
	}
	public void change(String[] Str, int start, int end) {
		if (start == 0) {
			result_n = 0;
		}
		
		if (start == end) {
			Point24(Str[0], Str[1], Str[2], Str[3]);
		} else {
			for (int i = start; i <= end; i++) {
				String temp = Str[start];
				Str[start] = Str[i];
				Str[i] = temp;

				change(Str, start + 1, end);
				temp = Str[start];
				Str[start] = Str[i];
				Str[i] = temp;
			}

		}
		// return Str;
	}

	public boolean IfNumber(char Chr) {

		if ((Chr == '0') || (Chr == '1') || (Chr == '2') || (Chr == '3')
				|| (Chr == '4') || (Chr == '5') || (Chr == '6') || (Chr == '7')
				|| (Chr == '8') || (Chr == '9')) {
			return true;

		} else {
			return false;
		}

	}

	int CharToIntByPoint(char[] Chr, int PointA, int PointB) {
		int FinalNum = 0;
		for (int i = PointA; i <= PointB; i++) {
			FinalNum = 10 * FinalNum + CharToInt(Chr[i]);
		}
		return FinalNum;

	}

	char[] CharToCharByPoint(char[] Chr, int PointA, int PointB) {
		char[] TempChar = new char[PointB - PointA + 1];
		int TempPoint = 0;
		for (int i = PointA; i <= PointB; i++) {
			TempChar[TempPoint] = Chr[i];
			TempPoint++;
		}
		return TempChar;

	}

	char IntToChar(char Chr) {

		return (char) (Chr + 48);
	}

	public LinkedHashSet<String> Point24(String N1, String N2, String N3,
			String N4) {
		String str_Exp = "";
		//int Result_n = 0;

		//LinkedHashSet<String> ansSet = new LinkedHashSet<>();
		
		// int Result_n=0;
		for (int i = 1; i <= 4; i++) {
			for (int i2 = 1; i2 <= 4; i2++) {
				for (int i3 = 1; i3 <= 4; i3++) {
					str_Exp = "(" + N1 + GetSymbolByOrd(i) + N2 + ")"
							+ GetSymbolByOrd(i2) + N3 + GetSymbolByOrd(i3) + N4;
					if (Math.abs(main(str_Exp) - 24) <= 0.00001f) {
						ansSet.add(str_Exp);

						// System.out.println(str_Exp);
						// System.out.println("= " + 24);
						result_n++;
					}

				}
			}
		}
		for (int i = 1; i <= 4; i++) {
			for (int i2 = 1; i2 <= 4; i2++) {
				for (int i3 = 1; i3 <= 4; i3++) {
					str_Exp = "(" + N1 + GetSymbolByOrd(i) + N2
							+ GetSymbolByOrd(i2) + N3 + ")"
							+ GetSymbolByOrd(i3) + N4;
					if (Math.abs(main(str_Exp) - 24) <= 0.00001f) {
						// System.out.print(str_Exp);
						// System.out.println("= " + 24);
						ansSet.add(str_Exp);
						result_n++;
					}

				}
			}
		}
		for (int i = 1; i <= 4; i++) {
			for (int i2 = 1; i2 <= 4; i2++) {
				for (int i3 = 1; i3 <= 4; i3++) {
					str_Exp = N1 + GetSymbolByOrd(i) + "(" + N2
							+ GetSymbolByOrd(i2) + N3 + ")"
							+ GetSymbolByOrd(i3) + N4;
					if (Math.abs(main(str_Exp) - 24) <= 0.00001f) {
						// System.out.print(str_Exp);
						// System.out.println("= " + 24);
						ansSet.add(str_Exp);

						result_n++;
					}

				}
			}
		}
		for (int i = 1; i <= 4; i++) {
			for (int i2 = 1; i2 <= 4; i2++) {
				for (int i3 = 1; i3 <= 4; i3++) {
					str_Exp = N1 + GetSymbolByOrd(i) + "(" + N2
							+ GetSymbolByOrd(i2) + N3 + GetSymbolByOrd(i3) + N4
							+ ")";
					if (Math.abs(main(str_Exp) - 24) <= 0.00001d) {
						// System.out.print(str_Exp);
						// System.out.println("= " + 24);
						ansSet.add(str_Exp);

						result_n++;
					}

				}
			}
		}
		for (int i = 1; i <= 4; i++) {
			for (int i2 = 1; i2 <= 4; i2++) {
				for (int i3 = 1; i3 <= 4; i3++) {
					str_Exp = N1 + GetSymbolByOrd(i) + N2 + GetSymbolByOrd(i2)
							+ "(" + N3 + GetSymbolByOrd(i3) + N4 + ")";
					if (Math.abs(main(str_Exp) - 24) <= 0.00001d) {
						// System.out.print(str_Exp);
						// System.out.println("= " + 24);
						ansSet.add(str_Exp);

						result_n++;
					}

				}
			}
		}
		for (int i = 1; i <= 4; i++) {
			for (int i2 = 1; i2 <= 4; i2++) {
				for (int i3 = 1; i3 <= 4; i3++) {
					str_Exp = "(" + N1 + GetSymbolByOrd(i) + N2 + ")"
							+ GetSymbolByOrd(i2) + "(" + N3
							+ GetSymbolByOrd(i3) + N4 + ")";
					if (Math.abs(main(str_Exp) - 24) <= 0.00001d) {
						// System.out.print(str_Exp);
						// System.out.println("= " + 24);
						ansSet.add(str_Exp);

						result_n++;
					}

				}
			}
		}
		// System.out.println(Result_n);
		return ansSet;
	}

	char GetSymbolByOrd(int Ord) {
		switch (Ord) {
		case 1:
			return '+';
		case 2:
			return '-';
		case 3:
			return '*';
		case 4:
			return '/';
		}
		return '=';
	}

	public float main(String str_Expression) {
		stack Mystack = new stack();
		char[] chr_Expression = str_Expression.toCharArray();
		float TempNum = 0, TempNum1 = 0, TempNum2 = 0;
		int Temp_Point_BracketA = 0, Temp_Point_BracketB = 0, Number_Point = 0, BracketNumber = 0;
		char TempSymbol = 0;
		boolean Bracket = false;

		for (int i = 0; i <= str_Expression.length() - 1; i++) { // 循環讀入所有的東西
			if ('(' == chr_Expression[i]) {
				Temp_Point_BracketA = i;
				BracketNumber = 1;
				while (BracketNumber != 0) {
					i++;
					if (chr_Expression[i] == '(') {
						BracketNumber += 1;

					} else if (chr_Expression[i] == ')') {
						BracketNumber -= 1;

					} else {
					}

				}
				Temp_Point_BracketB = i;
				TempNum = main(String.valueOf(CharToCharByPoint(chr_Expression,
						Temp_Point_BracketA + 1, Temp_Point_BracketB - 1)));
				Bracket = true;
				// 如果此時已經到了最後一個
				if (i == str_Expression.length() - 1) {
					Mystack.Push(TempNum);
					while (Mystack.stack_Num[0] != 1) {

						TempNum1 = Mystack.PopNum();
						Mystack.ClearLastNum();
						TempNum2 = Mystack.PopNum();
						Mystack.ClearLastNum();

						TempSymbol = Mystack.PopChar();
						Mystack.ClearLastChar();

						TempNum = go(TempNum2, TempNum1, TempSymbol);
						Mystack.Push(TempNum);
					}
				}
			} else if (!IfNumber(chr_Expression[i])) { // 遇到了符號
				if (!Bracket) {
					// 得到我們讀取到的數字
					TempNum = CharToIntByPoint(chr_Expression, Number_Point,
							i - 1);

				} else {
					Bracket = false;
				}
				// 做好標記
				Number_Point = i + 1;
				// 壓入堆棧
				Mystack.Push(TempNum);
				// System.out.println();

				// 判斷符號 如果優先級大於pop頂部的則壓入堆棧
				if (Getfirst(chr_Expression[i]) < Getfirst(Mystack.PopChar())) {
					// System.out.print(chr_Expression[i]);
					Mystack.Push(chr_Expression[i]);

					// 判斷符號 如果優先級小於等於則先計算後壓入
				} else {
					// 第一個數字就是pop出來的數字
					TempNum1 = Mystack.PopNum();
					// System.out.print(Mystack.stack_Num[0]);
					// 清掉前面一個數字
					Mystack.ClearLastNum();
					// System.out.print(Mystack.stack_Num[0]);
					TempNum2 = Mystack.PopNum();
					// 清掉數字
					Mystack.ClearLastNum();
					TempSymbol = Mystack.PopChar();
					Mystack.ClearLastChar();
					// System.out.print(math.go(TempNum2, TempNum1,
					// TempSymbol));
					TempNum = go(TempNum2, TempNum1, TempSymbol);
					Mystack.Push(TempNum);

					// 壓入等待的符號
					Mystack.Push(chr_Expression[i]);

				}
			} else if (i == str_Expression.length() - 1) { // 如果數字已經是最後一個了
				// System.out.println(math.CharToIntByPoint(chr_Expression,
				// Number_Point, i));

				TempNum = CharToIntByPoint(chr_Expression, Number_Point, i);
				Mystack.Push(TempNum);

				while (Mystack.stack_Num[0] != 1) {

					TempNum1 = Mystack.PopNum();
					Mystack.ClearLastNum();
					TempNum2 = Mystack.PopNum();
					Mystack.ClearLastNum();

					TempSymbol = Mystack.PopChar();
					Mystack.ClearLastChar();

					TempNum = go(TempNum2, TempNum1, TempSymbol);
					Mystack.Push(TempNum);
				}
				// System.out.print("= "+Mystack.stack_Num[1]);

			}

		}
		return Mystack.stack_Num[1];
	}
}