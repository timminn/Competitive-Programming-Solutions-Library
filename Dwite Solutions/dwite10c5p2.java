import java.util.*;
import java.io.*;
public class dwite10c5p2 {
	private static Reader in;
	private static PrintWriter out;
	
	private static int NUM_OF_TEST_CASES = 5; // TODO CHANGE NUMBER OF TEST CASES
	
	// TODO CHANGE FILE NAMES
	private static final String INPUT_FILE_NAME = "";
	private static final String OUTPUT_FILE_NAME = "";
	
    private static boolean crash = true;
    private static boolean flush = false;
    
	
	public static void main(String[]args) throws Exception {
		if (INPUT_FILE_NAME.equals("")) in = new Reader(System.in);
        else in = new Reader(INPUT_FILE_NAME);
        if (OUTPUT_FILE_NAME.equals("")) out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        else out = new PrintWriter(new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME)));
        for (int i = 1; i <= NUM_OF_TEST_CASES; i++) {
            try {
                run(i);
            } catch (Exception e) {
                System.err.println("Exception thrown on test case " + i);
                e.printStackTrace(System.err);
                out.flush();
                if (crash) throw new Exception();
            }
            if (flush) out.flush();
        }
        in.close();
        out.close();
		
	}
	public static void run(int testCaseNum) throws Exception {
		int r = in.readInt(), c = in.readInt(), ro = in.readInt();
		LinkedList<Point> rooks = new LinkedList<Point>();
		for(int i=1; i<=ro; i++)
			rooks.add(new Point(in.readInt(), in.readInt()));
		
		ArrayList<Integer> row = new ArrayList<Integer>();
		ArrayList<Integer> col = new ArrayList<Integer>();
		
		for(int i=1; i<=r; i++) {
			boolean ok = true;
			for(Point rook : rooks) {
				if(rook.x == i) {
					ok = false;
					break;
				}
			}
			if(ok) row.add(i);
		}
			
		for(int i=1; i<=c; i++) {
			boolean ok = true;
			for(Point rook : rooks) {
				if(rook.y == i) {
					ok = false;
					break;
				}
			}
			if(ok) col.add(i);
		}
		System.out.println(row.size() * col.size());
		
	}
	static class Point {
		int x, y;
		public Point(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	static void print(Object o) {
		out.print(o);
	}

	static void println(Object o) {
		out.println(o);
	}

	static void flush() {
		out.flush();
	}

	static void println() {
		out.println();
	}

	static void exit() throws IOException {
		in.close();
		out.close();
		System.exit(0);
	}
	static class Reader {
		
		final private static int BUFFER_SIZE = 1 << 16;
		private static DataInputStream din = new DataInputStream(System.in);
		private static byte[] buffer = new byte[BUFFER_SIZE];
		private static int bufferPointer = 0, bytesRead = 0;
		public Reader(InputStream stream) {
            din = new DataInputStream(stream);
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
		}
		
        public Reader(String fileName) throws IOException {
            din = new DataInputStream(new FileInputStream(fileName));
            buffer = new byte[BUFFER_SIZE];
            bufferPointer = bytesRead = 0;
        }
		
		public  String readLine() throws IOException {
			byte[] buf = new byte[64]; // line length
			int cnt = 0, c;
			while ((c = Read()) != -1) {
				if (c == '\n')
					break;
				buf[cnt++] = (byte) c;
			}
			return new String(buf, 0, cnt);
		}
		public  String read() throws IOException{
			byte[] ret = new byte[1024];
	        int idx = 0;
	        byte c = Read();
	        while (c <= ' ') {
	            c = Read();
	        }
	        do {
	            ret[idx++] = c;
	            c = Read();
	        } while (c != -1 && c != ' ' && c != '\n' && c != '\r');
	        return new String(ret, 0, idx);
		}
		public int readInt() throws IOException {
			int ret = 0;
			byte c = Read();
			while (c <= ' ')
				c = Read();
			boolean neg = (c == '-');
			if (neg)
				c = Read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = Read()) >= '0' && c <= '9');
	
			if (neg)
				return -ret;
			return ret;
		}
	
		public  long readLong() throws IOException {
			long ret = 0;
			byte c = Read();
			while (c <= ' ')
				c = Read();
			boolean neg = (c == '-');
			if (neg)
				c = Read();
			do {
				ret = ret * 10 + c - '0';
			} while ((c = Read()) >= '0' && c <= '9');
			if (neg)
				return -ret;
			return ret;
		}
	
		public  double readDouble() throws IOException {
			double ret = 0, div = 1;
			byte c = Read();
			while (c <= ' ')
				c = Read();
			boolean neg = (c == '-');
			if (neg)
				c = Read();
	
			do {
				ret = ret * 10 + c - '0';
			} while ((c = Read()) >= '0' && c <= '9');
	
			if (c == '.') {
				while ((c = Read()) >= '0' && c <= '9') {
					ret += (c - '0') / (div *= 10);
				}
			}
	
			if (neg)
				return -ret;
			return ret;
		}
	
		private  void fillBuffer() throws IOException {
			bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
			if (bytesRead == -1)
				buffer[0] = -1;
		}
	
		private  byte Read() throws IOException {
			if (bufferPointer == bytesRead)
				fillBuffer();
			return buffer[bufferPointer++];
		}
	
		public void close() throws IOException {
			if (din == null)
				return;
			din.close();
		}
	}
}
