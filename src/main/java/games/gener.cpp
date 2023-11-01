#include <bits/stdc++.h>
using namespace std;

#define all(x) (x).begin(), (x).end()
using str = string; using pii = pair<int, int>;

#define rpt(i, u, v) for (ll i = (u), _n_ = (v); i < _n_; i++)
#define tpr(i, u, v) for (ll i = (u), _n_ = (v); --i >= _n_; )

template<class A, class B> bool chmax(A &a, const B b) { return a<b&&(a=b,1); }

namespace {
    using ll = long long;
    mt19937_64 rng(chrono::steady_clock::now().time_since_epoch().count());
    static const ll FIXED_RANDOM = rng(); // use this instead of rand()
    ll rand(ll a, ll b) { return uniform_int_distribution<ll>(a, b)(rng); }
} // random number generators



#define x first
#define y second

const int MAX_ERRORS = 30;
int SIZE, SHRT, LONG, WORDNUM;

const int MAX_ERRORS = 30;

vector<pair<pii, pii>> old, res_pairs;

pii fill_word(vector<str> &matrix, const str &word) {
    pii start, end = {-1, -1};
    int leng = word.size();

    int angle = rand(1, 3), cnt = 0;
    start.y = rand(0, angle == 1 ? SIZE - 1 : SIZE - leng);
    start.x = rand(0, angle == 2 ? SIZE - 1 : SIZE - leng);

    rpt (j, 0, leng) {
        char c = [&]() -> char {
            if (angle == 1) return matrix[start.y][start.x+j];
            if (angle == 2) return matrix[start.y+j][start.x];
            if (angle == 3) return matrix[start.y+j][start.x+j];
            return '?';
        }();
        if (c != '.' && c != word[j]) return end;
    }

    end.y = start.y + (angle == 1 ? 0 : leng-1);
    end.x = start.x + (angle == 2 ? 0 : leng-1);

    rpt (j, 0, leng) {
        if (angle == 1) matrix[start.y][start.x+j]   = word[j];
        if (angle == 2) matrix[start.y+j][start.x]   = word[j];
        if (angle == 3) matrix[start.y+j][start.x+j] = word[j];
    }

    old.emplace_back(start, end);
    return {angle, cnt};
}

int fill_matrix(vector<str> &matrix, const vector<str> &wordlist) {
    vector<int> res(4, 0);
    old.clear();

    for (int i = 0; i < WORDNUM; i++) {
        str word = wordlist[i];
        int remains = MAX_ERRORS;

        while (remains > 0) {
            auto [angle, cnt] = fill_word(matrix, word);
            if (angle == -1) remains--;
            else {
                res[angle]++;
                res[0]++;
                break;
            }
        }

        if (remains == 0) return -1;
    }

    int point = 1;
    point *= res[0] * res[0] * res[0];
    point *= res[1] * res[2] * res[3];
    return point;
}

int main() {
    cin.tie(nullptr) -> sync_with_stdio(false);

    SIZE = stoi(argv[1]);
    SHRT = stoi(argv[2]);
    LONG = stoi(argv[3]);
    WORDNUM = SHRT + LONG;

    vector<str> long_wordlist;
    vector<str> shrt_wordlist;

    ifstream longnote("src/main/java/games/long.txt");
    ifstream shrtnote("src/main/java/games/short.txt");

    for (str s; longnote >> s; ) {
        long_wordlist.push_back(s);
    }

    for (str s; shrtnote >> s; ) {
        shrt_wordlist.push_back(s);
    }

    vector<str> best;
    vector<str> resp;
    int point = 0;

    vector<str> matrix(SIZE);
    for (int tries = 0; tries < 500000 || best.empty(); tries++) {
        shuffle(all(long_wordlist), rng);
        shuffle(all(shrt_wordlist), rng);

        vector<str> wordlist;
        rpt (i, 0, LONG) wordlist.push_back(long_wordlist[i]);
        rpt (i, 0, SHRT) wordlist.push_back(shrt_wordlist[i]);
        shuffle(all(wordlist), rng);

        fill(all(matrix), str(SIZE, '.'));
        int cnt = fill_matrix(matrix, wordlist);

        if (chmax(point, cnt)) {
            best = matrix;
            resp = wordlist;
            res_pairs = old;
        }
    }

    for (int i = 0; i < WORDNUM; i++) {
        cout << resp[i] << ' ';
        cout << res_pairs[i].x.y << ' ';
        cout << res_pairs[i].x.x << ' ';
        cout << res_pairs[i].y.y << ' ';
        cout << res_pairs[i].y.x << '\n';
    }
    rpt (i, 0, SIZE) {
        for (char &c : best[i]) {
            if (c != '.') continue;
            c = rand('a', 'z');
        }
        cout << best[i] << '\n';
    }
}