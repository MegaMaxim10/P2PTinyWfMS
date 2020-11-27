module Reconciliation where

data Gram prod symb = Gram {prods :: [prod], symbols :: [symb],lhs :: prod ->symb, rhs :: prod ->[symb]}

data AST a = BUD | NODE{node_label::a, unNODE::[AST a]} deriving Eq

instance (Show a) => Show (AST a) where 
 show BUD = "BUD[]"
 show (NODE a ts) = (show a)++"["++(printWithSep "," (map show ts))++"]"

printWithSep sep [] = ""
printWithSep sep [xs] = xs
printWithSep sep (xs:xss) = xs++sep++(printWithSep sep xss)

dist :: [[a]] -> [[a]]
dist [ ] = [[ ]]
dist (xs : xss) = [y : ys | y <- xs, ys <- dist xss]

data Automata a b = Auto{exit:: b-> Bool, next :: b -> [(a,[b])]}

enum :: (Eq b) => Automata a b -> b -> [AST a]
enum auto init = enum_ [] init
  where enum_ path state | elem state path = []
						| otherwise = (	if ((exit auto state) && (null (next auto state) )) then [BUD]
									else []) ++ 
                                    [NODE elet list_tree | 
                                            (elet, states) <- next auto state,
				            list_tree <- dist (map (enum_ (state : path)) states)]
				   
data BTree a = BTree{value::a, sons::Maybe [BTree a]} deriving Eq

projection :: (symb -> Bool) -> BTree symb -> [BTree symb]
projection view t@(BTree a Nothing) = if view a then [t] else []
projection view t@(BTree a (Just xs)) = if view a then [BTree a (Just sons_)]
										else sons_
											where 
												sons_ = concat (map (projection view) xs)

instance (Show a) => Show (BTree a) where 
	show (BTree a Nothing) = (show a) ++ "omega[]"
	show (BTree a (Just ts)) = (show a)++"["++(printWithSep "," (map show ts))++"]"

bud :: a -> BTree a 
bud a = BTree a Nothing

data Tag x = Close x | Open x deriving (Eq,Show)
untag :: Tag x -> x
untag (Open x) = x
untag (Close x) =x 

gram2autoview gram symb2prod view  = Auto exit next
  where
        exit (Open symb,[]) = True 
        exit _ = False 
        next (Open symb,_) = [(symb2prod symb,[])]
        next (Close symb,ts) = 
              [(p, zipWith trans (rhs gram p) tss) | 
                        p <- prods gram,
                        symb == lhs gram p,
						tss <- match_ view (rhs gram p) ts]

trans symb (Close ts) = (Close symb, ts)
trans symb (Open  ts) = (Open  symb, ts)

match_ :: (Eq symb)=> (symb -> Bool) -> [symb] -> [BTree symb] -> [[Tag [BTree symb]]]
match_ view [] ts = if null ts then [[]] else []
match_ view (symb:symbs) ts =  [(ts1:tss)| (ts1,ts2) <- matchone_ view symb ts,
											tss <- match_ view symbs ts2]

matchone_ :: (Eq symb) => (symb -> Bool) -> symb -> [BTree symb]
                          -> [(Tag [BTree symb],[BTree symb])]
matchone_ view symb ts = 
  if view symb
  then if null ts then []
       else case (head ts) of 
                 (BTree symb' Nothing) -> if symb==symb'
                                          then [(Open [], tail ts)]
										  else []
                 (BTree symb' (Just ts')) -> if symb==symb' 
                                             then [(Close ts', tail ts)]
                                             else []
  else [(Open [], ts)]++
       [(Close (take n ts),drop n ts) | n <- [1..(length ts)]]

autoconsens symb2prod auto1  auto2 = Auto exit_ next_ where
	exit_ (state1,state2) = case haveConsensus state1 state2 of
					True -> (exit auto1 state1) && (exit auto2 state2) 
					False -> True
	next_ (state1,state2) = case haveConsensus state1 state2 of
		False -> []
		True  -> case (exit1,exit2) of 
			(False,False) -> case (isInConflicts state1 state2) of
												True -> [(symb2prod(typeState state1),[])]					
												False -> [(a1,zip states1 states2) | (a1,states1) <- next auto1 state1,
																					(a2,states2) <- next auto2 state2,
																					a1==a2,
																	(length states1)==(length states2)]									
			(False,True)  -> [(a, zip states1 (forwardSleepState  states1))
												| 	(a,states1) <- next auto1 state1]
			(True,False)  -> [(a, zip (forwardSleepState  states2) states2)
												| 	(a,states2) <- next auto2 state2]
			(True,True)   -> [(a1,[]) | 	(a1,[]) <- next auto1 state1,
													(a2,[]) <- next auto2 state2,
													a1==a2
													]
		where 
			exit1 = exit auto1 state1
			exit2 = exit auto2 state2
			forwardSleepState states = map (\state -> (Open (typeState state), [])) states
			typeState state@(tagsymb, ts1) = (untag tagsymb)
			isInConflicts state1@(tagsymb1, ts1) state2@(tagsymb2, ts2) = 
								null [(a1,zip states1 states2) | (a1,states1) <- next auto1 state1,
															(a2,states2) <- next auto2 state2,
															a1==a2,
															(length states1)==(length states2)]

															
haveConsensus state1@(tagsymb1, ts1) state2@(tagsymb2, ts2)=  (untag tagsymb1) == (untag tagsymb2)	

freeDouble xs = f [] xs where 	
			f xs [] = xs
			f xs (y:ys) = case (elem y xs) of
				True -> f xs ys
				False -> f (xs ++ [y]) ys

